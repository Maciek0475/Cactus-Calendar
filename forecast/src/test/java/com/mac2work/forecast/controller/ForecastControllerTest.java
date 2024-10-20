package com.mac2work.forecast.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.LinkedList;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.cactus_library.model.DailyForecast;
import com.mac2work.forecast.service.ForecastService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ForecastController.class)
class ForecastControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ForecastService forecastService;
	
	private Double lat;
	private Double lon;
	private DailyForecast dailyForecast;
	private DailyForecast dailyForecast2;
	@BeforeEach
	void setUp() throws Exception {
		lat = 52.74;
		lon = 15.23;
		
		dailyForecast = DailyForecast.builder()
				.temp(17.5)
				.humidity(66.1)
				.date(LocalDate.parse("2024-10-01"))
				.success(40.0)
				.build();
		dailyForecast2 = DailyForecast.builder()
				.temp(13.0)
				.humidity(63.0)
				.date(LocalDate.parse("2024-10-02"))
				.success(31.0)
				.build();
	}

	@Test
	void forecastController_getForecast_ReturnMoreThanOneDailyForecast() throws Exception {
		LinkedList<DailyForecast> dailyForecasts = new LinkedList<>();
		dailyForecasts.add(null);
		dailyForecasts.add(dailyForecast);
		dailyForecasts.add(dailyForecast2);
		when(forecastService.getForecast(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(dailyForecasts);
		
		ResultActions result = mockMvc.perform(
				get("/api/forecast/"+lat+"/"+lon)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.size()", CoreMatchers.is(dailyForecasts.size())));
		
		
	}

	@Test
	void forecastController_getDailyForecast_ReturnDailyForecast() throws Exception {
		int dayOfMonth = dailyForecast.getDate().getDayOfMonth();
		when(forecastService.getDailyForecast(dayOfMonth, lat, lon)).thenReturn(dailyForecast);
		
		ResultActions result = mockMvc.perform(
				get("/api/forecast/"+dayOfMonth+"/"+lat+"/"+lon)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.temp", CoreMatchers.is(dailyForecast.getTemp())))
			.andExpect(jsonPath("$.humidity", CoreMatchers.is(dailyForecast.getHumidity())))
			.andExpect(jsonPath("$.date", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(dailyForecast.getDate()), Object.class))))
			.andExpect(jsonPath("$.success", CoreMatchers.is(dailyForecast.getSuccess())));
		
	}

}
