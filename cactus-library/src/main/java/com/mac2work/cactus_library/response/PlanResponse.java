package com.mac2work.cactus_library.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanResponse {
	private Long id;
	private LocalDate date;
	private String note;
	private Double successPropability;
	private CityResponse cityResponse;
	private boolean isDone;
	private Long userId;
}
