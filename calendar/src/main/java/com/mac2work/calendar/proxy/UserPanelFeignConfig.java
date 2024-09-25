package com.mac2work.calendar.proxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;

import com.google.common.net.HttpHeaders;
import com.mac2work.calendar.config.UserRepository;
import com.mac2work.calendar.service.JwtService;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPanelFeignConfig {
	
	private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);
	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	@Bean 
	RequestInterceptor feignRequestInterceptor() {
		return template -> {
			final String authorization = HttpHeaders.AUTHORIZATION;
			String token = jwtService.generateToken();
			String authorizationHeader = "Bearer " + token;
			String username = jwtService.extractUsername(token);
			Long userId = userRepository.findByUsername(username).orElseThrow().getId();
		        Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
		        if (matcher.matches()) {
		            template.header(authorization);
		            template.header(authorization, authorizationHeader);
		            template.header("LoggedUsername", username);
		            template.header("LoggedUserId", String.valueOf(userId));
		            
		        }
		};
	}
}
