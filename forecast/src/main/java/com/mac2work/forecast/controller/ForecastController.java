package com.mac2work.forecast.controller;

import java.util.LinkedList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.forecast.model.DailyForecast;
import com.mac2work.forecast.service.ForecastService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forecast")
public class ForecastController {

	private final ForecastService forecastService;
	
	@GetMapping("/{lat}/{lon}")
	public LinkedList<DailyForecast> getForecast(@PathVariable Double lat, @PathVariable Double lon){
		LinkedList<DailyForecast> dailyForecasts = forecastService.getForecast(lat, lon);
		return dailyForecasts;
	}
	@GetMapping("/{month}/{lat}/{lon}")
	public DailyForecast getDailyForecast(@PathVariable Integer month, @PathVariable Double lat, @PathVariable Double lon){
		DailyForecast dailyForecast= forecastService.getDailyForecast(month, lat, lon);
		return dailyForecast;
	}
}
