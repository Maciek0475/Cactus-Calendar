package com.mac2work.plans.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanResponse {
	private LocalDate date;
	private String note;
	private Double successPropability;
	private Long cityId;
	private boolean isDone;
	private Long userId;
}
