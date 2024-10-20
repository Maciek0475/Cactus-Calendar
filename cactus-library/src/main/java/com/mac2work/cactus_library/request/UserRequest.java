package com.mac2work.cactus_library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	@NotBlank(message="Username may not be empty")
	@Size(min=5, max=45, message="Username must be at least 5 characters long")
	private String username;
	@NotBlank(message="Password may not be empty")
	@Pattern(regexp="(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[A-Z])(?=.*[0-9])(?=.{8,}).*$", 
			message="password must be at least 8 characters long, contain 1 capital letter and 1 digit")
	private String password;
}
