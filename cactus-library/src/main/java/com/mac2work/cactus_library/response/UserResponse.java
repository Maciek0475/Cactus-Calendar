package com.mac2work.cactus_library.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
	private String username;
	private String password;
	private List<PlanResponse> planResponses;
	private CityResponse cityResponse;
}
