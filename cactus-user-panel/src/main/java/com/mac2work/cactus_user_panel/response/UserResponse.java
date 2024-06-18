package com.mac2work.cactus_user_panel.response;

import com.mac2work.cactus_user_panel.model.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private String username;
	private Role role;
	private CityResponse city;
}
