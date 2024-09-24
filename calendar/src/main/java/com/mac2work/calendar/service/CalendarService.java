package com.mac2work.calendar.service;

import java.util.LinkedList;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.model.DailyForecast;
import com.mac2work.calendar.proxy.ForecastServiceProxy;
import com.mac2work.calendar.proxy.UserPanelProxy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
	private final ForecastServiceProxy forecastServiceProxy;
	private final UserPanelProxy userPanelProxy;

	public String forecast( Model model) {
		CityResponse city = getCity();
		boolean isCityChosen = city != null;
		if(isCityChosen) {
		LinkedList<DailyForecast> dailyForecasts = forecastServiceProxy.getForecast(city.getLat(), city.getLon());
		model.addAttribute("isCityChosen", isCityChosen);
		model.addAttribute("dailyForecasts", dailyForecasts);
		}
		return "calendar.html";
	}

	private CityResponse getCity() {
		UserResponse userResponse = userPanelProxy.getAccountInfo();
		return userResponse.getCityResponse();
	}

	public String getDailyForecast(Integer dayOfMonth, PlanRequest plan, Model model) {
		CityResponse cityResponse = getCity();
		DailyForecast dailyForecast = forecastServiceProxy.getDailyForecast(dayOfMonth, cityResponse.getLat(), cityResponse.getLon());
		model.addAttribute("city", cityResponse);
		model.addAttribute("plan", plan);
		model.addAttribute("dailyForecast", dailyForecast);
		return "new-plan.html";
	}

}