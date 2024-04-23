package com.mac2work.myfirstproject.webapp.response;

import java.util.List;

import com.mac2work.myfirstproject.webapp.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private String username;
	private String password;
	private Role role;
	private List<PlanResponse> planResponses;
	private CityResponse cityResponse;
}
