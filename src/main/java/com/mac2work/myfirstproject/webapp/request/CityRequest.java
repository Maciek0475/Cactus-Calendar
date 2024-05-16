package com.mac2work.myfirstproject.webapp.request;

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
public class CityRequest {
	@NotEmpty(message = "name must not be empty")
	private String name;
	@NotNull(message = "lat must not be null")
	private Double lat;
	@NotNull(message = "lon must not be null")
	private Double lon;

}
