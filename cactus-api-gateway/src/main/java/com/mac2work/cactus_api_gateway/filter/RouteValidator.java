package com.mac2work.cactus_api_gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	
	public static final List<String> unsecApiEndpoints = List.of(
			"/calendar",
			"/error",
			"/eureka",
			"/api/panel/auth"
			);
	
	 public Predicate<ServerHttpRequest> isSecured =
	            request -> unsecApiEndpoints
	                    .stream()
	                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
