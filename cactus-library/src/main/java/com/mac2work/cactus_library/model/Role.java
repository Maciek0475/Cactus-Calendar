package com.mac2work.cactus_library.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role{
	USER,
	ADMIN;
	
	@JsonValue
	public String toString() {
		return super.toString().toLowerCase();
	}
}
