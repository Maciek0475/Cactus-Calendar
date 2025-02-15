package com.mac2work.calendar.proxy;

import java.util.LinkedList;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mac2work.cactus_library.model.DailyForecast;

@FeignClient(name="FORECAST-SERVICE", configuration = {FeignConfig.class})
public interface ForecastServiceProxy {
	
	@GetMapping("/api/forecast/{month}/{lat}/{lon}")
	DailyForecast getDailyForecast(@PathVariable Integer month, @PathVariable Double lat, @PathVariable Double lon);
	
	@GetMapping("/api/forecast/{lat}/{lon}")
	LinkedList<DailyForecast> getForecast(@PathVariable Double lat, @PathVariable Double lon);

}
