package com.mac2work.myfirstproject.webapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponse {
	private String name;
	private Double lat;
	private Double lon;
}
