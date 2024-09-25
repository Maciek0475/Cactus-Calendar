package com.mac2work.cactus_library.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyForecast {
	private double temp;
	private double humidity;
	private LocalDate date;
	private double success;
}
