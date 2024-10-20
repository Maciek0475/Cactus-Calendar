package com.mac2work.cactus_library.request;

import java.time.LocalDate;

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
}
