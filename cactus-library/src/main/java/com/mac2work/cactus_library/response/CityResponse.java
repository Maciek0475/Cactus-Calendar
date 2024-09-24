package com.mac2work.cactus_library.response;

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
