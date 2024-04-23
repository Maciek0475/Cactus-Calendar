package com.mac2work.myfirstproject.webapp.request;

import java.util.List;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.model.Plan;
import com.mac2work.myfirstproject.webapp.model.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
	@NotEmpty(message="username must not be empty")
	private String username;
	@NotEmpty(message="password must not be empty")
	private String password;
	@NotNull(message="role must not be empty")
	private Role role;
	private List<Plan> plans;
	private City city;


}
