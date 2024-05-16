package com.mac2work.myfirstproject.webapp.request;

import java.time.LocalDate;
import java.util.List;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.model.Plan;
import com.mac2work.myfirstproject.webapp.model.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequest {
	@NotNull(message="date must not be null")
	private LocalDate date;
	@NotEmpty(message="note must not be empty")
	private String note;
	@NotNull(message="success propability must not be null")
	private Double successPropability;
	@NotNull(message="city id must not be null")
	private Long cityId;
	@NotNull(message="user id must not be null")
	private Long userId;

}
