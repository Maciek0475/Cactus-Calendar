package com.mac2work.calendar.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponse {
	private Long id;
	private String name;
	private Double lat;
	private Double lon;
}
