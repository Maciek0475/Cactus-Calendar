package com.mac2work.myfirstproject.webapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UserDto {

	@NotBlank(message="Username may not be empty")
	@Size(min=5, max=45, message="Username must be at least 5 characters long")
	private final String username;
	
	@Pattern(regexp="(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[A-Z])(?=.*[0-9])(?=.{8,}).*$", 
	message="password must be at least 8 characters long, contain 1 capital letter and 1 digit")
	private final String password;
	
	public UserDto(String username, String password, String email) {
		this.username = username;
		this.password = password;

	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	

	@Override
	public String toString() {
		return "RegistrationRequest [username=" + username + ", password=" + password + "]";
	}
	

	
}
