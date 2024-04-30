package com.mac2work.myfirstproject.webapp.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanResponse {
	private LocalDate date;
	private String note;
	private Double successPropability;
	private CityResponse cityResponse;
	private boolean isDone;
	private UserResponse userResponse;
}
