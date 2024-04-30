package com.mac2work.myfirstproject.webapp.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role{
	USER,
	ADMIN;
	
	@JsonValue
	public String toString() {
		return super.toString().toLowerCase();
	}
}
