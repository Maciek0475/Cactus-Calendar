package com.mac2work.forecast.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.cactus_library.model.DailyForecast;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForecastService {
	@Value("${mac2work.forecast.good.temperature}")
	private Double GOOD_TEMP;
	@Value("${mac2work.forecast.good.humidity}")
	private Double GOOD_HUMIDITY;
	@Value("${mac2work.forecast.multiplier.temperature}")
	private Double TEMPERATURE_MULTIPLIER;
	@Value("${mac2work.forecast.multiplier.humidity}")
	private Double HUMIDITY_MULTIPLIER;
	@Value("${mac2work.forecast.url.prefix}")
	private String API_URL_PREFIX;
	@Value("${mac2work.forecast.url.suffix}")
	private String API_URL_SUFFIX;

	@SuppressWarnings("unchecked")
	public <T1, T2> LinkedList<DailyForecast> getForecast(Double lat, Double lon) {
		String FORECAST_API_URL = API_URL_PREFIX
				+ lat
				+","
				+ lon
				+ API_URL_SUFFIX;
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(FORECAST_API_URL, String.class);
		HashMap<T1, T2> map = null;

		try {
			map = new ObjectMapper().readValue(result, HashMap.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ArrayList<HashMap<T1, T2>> days = (ArrayList<HashMap<T1, T2>>) map.get("days");

		LinkedList<DailyForecast> dailyForecasts = days.stream().map(
				day -> DailyForecast.builder()	
				.temp((double) day.get("temp"))
				.humidity((double) day.get("humidity"))
				.date(LocalDate.parse((CharSequence) day.get("datetime")))
				.build())
				.collect(Collectors.toCollection(LinkedList::new));
		
		for (int i = 1; i < LocalDate.now().getDayOfWeek().getValue(); i++)
			dailyForecasts.addFirst(null);
		
		return dailyForecasts.stream().
				map(dailyForecast -> calculateSuccess(dailyForecast)).collect(Collectors.toCollection(LinkedList::new));
	}
	
	public DailyForecast calculateSuccess(DailyForecast dailyForecast) {
		if (dailyForecast != null) {
			
			dailyForecast.getTemp();
			Double humiditySuccess = Math.abs(dailyForecast.getHumidity() - GOOD_HUMIDITY);
			Double tempSuccess = dailyForecast.getTemp() - GOOD_TEMP;
			humiditySuccess = ((100 - humiditySuccess * HUMIDITY_MULTIPLIER) / 100 < 0) ? 0
					: (100 - humiditySuccess * HUMIDITY_MULTIPLIER) / 100;
			
			Double finalTempMultiplier = 1.0;
			if (tempSuccess < 0)
				finalTempMultiplier = TEMPERATURE_MULTIPLIER * -1.5;
			tempSuccess = ((100 - tempSuccess * finalTempMultiplier) / 100 < 0) ? 0
					: (100 - tempSuccess * finalTempMultiplier) / 100;
			dailyForecast.setSuccess(Double.valueOf( (int) (tempSuccess * humiditySuccess * 100) * 100 ) / 100);
			
			return dailyForecast;
		}
		return null;
	}
	public DailyForecast getDailyForecast(Integer dayOfMonth, Double lat, Double lon) {
		DailyForecast dailyForecast = getForecast(lat, lon).stream().filter(d -> Objects.nonNull(d))
				.filter(d -> d.getDate().getDayOfMonth() == dayOfMonth).findFirst().get();
		return dailyForecast;
	}

}
