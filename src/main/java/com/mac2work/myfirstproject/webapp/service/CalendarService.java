package com.mac2work.myfirstproject.webapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.myfirstproject.webapp.model.DailyForecast;
import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

@Service
public class CalendarService {
	private final double goodTemp = 21;
	private final double goodHumidity = 50;
	private final UserRepository userRepository;
	private double humiditySuccess;
	private double tempSuccess;
	private List<DailyForecast> dailyForecasts;

	public CalendarService(UserRepository userRepository) {
		this.userRepository = userRepository;
	} 

	public List<DailyForecast> forecast(Double...geoArgs) {
		
		Double lat = (geoArgs.length == 2)? geoArgs[0] : getLoggedUser().getCity().getLat();
		Double lon = (geoArgs.length == 2)? geoArgs[1] : getLoggedUser().getCity().getLon();
		String FORECAST_API_URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
				+ lat
				+","
				+ lon
				+ "?unitGroup=metric&elements=datetime,temp,humidity,icon&include=days&key=3XQ3BGCW8Y9TEGLCV9642PV7D&contentType=json";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(FORECAST_API_URL, String.class);
		HashMap<String, Object> map = null;

		try {
			map = new ObjectMapper().readValue(result, HashMap.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		ArrayList<HashMap<String, Object>> days = (ArrayList<HashMap<String, Object>>) map.get("days");
		List<DailyForecast> dailyForecasts = new ArrayList<>();

		for (int i = 1; i < LocalDate.now().getDayOfWeek().getValue(); i++)
			dailyForecasts.add(null);

		for (HashMap<String, Object> day : days) {
			dailyForecasts.add(new DailyForecast((double) day.get("temp"), (double) day.get("humidity"),
					LocalDate.parse((CharSequence) day.get("datetime"))));
		}
		dailyForecasts = calculateSuccess(dailyForecasts);
		this.dailyForecasts = dailyForecasts;
		return dailyForecasts;
	}

	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			user = userRepository.findByUsername(currentUsername).get();
		}
		return user;

	}

	public List<DailyForecast> calculateSuccess(List<DailyForecast> dailyForecasts) {

		for (DailyForecast dailyForecast : dailyForecasts) {
			if (dailyForecast != null) {
				dailyForecast.getTemp();
				humiditySuccess = Math.abs(dailyForecast.getHumidity() - goodHumidity);
				tempSuccess = dailyForecast.getTemp() - goodTemp;
				double tempMultiplier = 4;
				double humidityMultiplier = 3;
				humiditySuccess = ((100 - humiditySuccess * humidityMultiplier) / 100 < 0) ? 0
						: (100 - humiditySuccess * humidityMultiplier) / 100;
				if (tempSuccess < 0)
					tempMultiplier *= -1.5;
				tempSuccess = ((100 - tempSuccess * tempMultiplier) / 100 < 0) ? 0
						: (100 - tempSuccess * tempMultiplier) / 100;
				dailyForecast.setSuccess(tempSuccess * humiditySuccess * 100);

			}
		}
		return dailyForecasts;
	}

	public DailyForecast getDailyForecast(int dayOfMonth) {
		Optional<DailyForecast> dailyForecast = dailyForecasts.stream().filter(d -> Objects.nonNull(d))
				.filter(d -> d.getDate().getDayOfMonth() == dayOfMonth).findFirst();
		return dailyForecast.get();
	}

}
