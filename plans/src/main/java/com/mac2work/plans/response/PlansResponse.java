package com.mac2work.plans.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlansResponse {
	
	private Boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

}
