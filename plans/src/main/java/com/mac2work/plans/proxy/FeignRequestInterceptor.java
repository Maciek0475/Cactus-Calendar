package com.mac2work.plans.proxy;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.net.HttpHeaders;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignRequestInterceptor implements RequestInterceptor{
	private static final Pattern BEARER_TOKEN_HEADER_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);
	
	@Override
	public void apply(RequestTemplate template) {
		final String authorization = HttpHeaders.AUTHORIZATION;
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (Objects.nonNull(requestAttributes)) {
	        String authorizationHeader = requestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
	        Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
	        if (matcher.matches()) {
	            template.header(authorization);
	            template.header(authorization, authorizationHeader);
	        }
	    }
	}
	
	
}
