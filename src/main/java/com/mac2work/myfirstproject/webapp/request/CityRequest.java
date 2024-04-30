package com.mac2work.myfirstproject.webapp.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityRequest {
	@NotEmpty(message = "name must not be empty")
	private String name;
	@NotNull(message = "lat must not be null")
	private Double lat;
	@NotNull(message = "lon must not be null")
	private Double lon;

}
