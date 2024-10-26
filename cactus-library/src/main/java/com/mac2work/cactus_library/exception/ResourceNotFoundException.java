package com.mac2work.cactus_library.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2637783630843958484L;

	private String fieldName;
	private Object fieldValue;
	private String resourceName;
	private String message;
	
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.resourceName = resourceName;
		formatMessage();		
	}
	
	private void formatMessage() {
		this.message = String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);
	}

}
