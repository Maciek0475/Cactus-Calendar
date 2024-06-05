package com.mac2work.forecast.controller;

import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<LinkedList<DailyForecast>> getForecast(@PathVariable Double lat, @PathVariable Double lon){
		LinkedList<DailyForecast> dailyForecasts = forecastService.getForecast(lat, lon);
		return new ResponseEntity<>(dailyForecasts, HttpStatus.OK);
	}
	@GetMapping("/{month}/{lat}/{lon}")
	public ResponseEntity<DailyForecast> getDailyForecast(@PathVariable Integer month, @PathVariable Double lat, @PathVariable Double lon){
		DailyForecast dailyForecast= forecastService.getDailyForecast(month, lat, lon);
		return new ResponseEntity<>(dailyForecast, HttpStatus.OK);
	}
}
