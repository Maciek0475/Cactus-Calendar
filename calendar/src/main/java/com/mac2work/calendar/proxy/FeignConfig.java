package com.mac2work.calendar.proxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;

import com.google.common.net.HttpHeaders;
import com.mac2work.calendar.service.JwtService;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignConfig {
	
	private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);
	private final JwtService jwtService;
	
	@Bean 
	RequestInterceptor feignRequestInterceptor() {
		return template -> {
			final String authorization = HttpHeaders.AUTHORIZATION;
			String authorizationHeader = "Bearer " + jwtService.generateToken();
		        Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
		        if (matcher.matches()) {
		            template.header(authorization);
		            template.header(authorization, authorizationHeader);
		        }
		};
	}
}
