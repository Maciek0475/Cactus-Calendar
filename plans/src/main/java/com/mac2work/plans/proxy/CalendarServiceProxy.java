package com.mac2work.plans.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="CALENDAR-SERVICE")
public interface CalendarServiceProxy {

	@GetMapping("")
	public double getDailyForecastSuccess(int dayOfMonth);

}
