package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.model.DailyForecast;
import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.proxy.ForecastServiceProxy;
import com.mac2work.calendar.proxy.UserPanelProxy;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {
	
	@Mock
	private ForecastServiceProxy forecastServiceProxy;
	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private CalendarService calendarService;

	private DailyForecast dailyForecast;
	private DailyForecast dailyForecast2;
	private CityResponse cityResponse;
	private UserResponse userResponse;
	private PlanRequest planRequest;
	
	@BeforeEach
	void setUp() throws Exception {		
		dailyForecast = DailyForecast.builder()
				.temp(17.5)
				.humidity(66.1)
				.success(40.0)
				.date(LocalDate.parse("2024-10-01"))
				.build();
		dailyForecast2 = DailyForecast.builder()
				.temp(13.0)
				.humidity(63.0)
				.success(31.0)
				.date(LocalDate.parse("2024-10-02"))
				.build();
		cityResponse = CityResponse.builder()
				.name("Gorzów Wielkopolski")
				.id(1L)
				.lat(52.74)
				.lon(15.23)
				.build();
		userResponse = UserResponse.builder()
				.username("mac2work@gmail.com")
				.cityResponse(cityResponse)
				.planResponses(null)
				.build();
		planRequest = PlanRequest.builder()
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.build();
	}
	

	@Test
	void calendarService_forecast_CheckIfModelIsCorrectAndReturnHtmlFileName() {
		boolean isCityChoosen = true;
		LinkedList<DailyForecast> dailyForecasts = new LinkedList<>();
		dailyForecasts.add(dailyForecast);
		dailyForecasts.add(dailyForecast2);
		when(forecastServiceProxy.getForecast(cityResponse.getLat(), cityResponse.getLon())).thenReturn(dailyForecasts);
		when(userPanelProxy.getAccountInfo()).thenReturn(userResponse);
		Model model = new ExtendedModelMap();
		
		String response = calendarService.forecast(model);
		
		assertThat(response).isNotEmpty();
		assertThat(model.getAttribute("isCityChosen")).isEqualTo(isCityChoosen);
		assertThat(model.getAttribute("dailyForecasts")).isEqualTo(dailyForecasts);
	}

	@Test
	void calendarService_getDailyForecast_CheckIfModelIsCorrectAndReturnHtmlFileName() {
		int dayOfMonth = dailyForecast.getDate().getDayOfMonth();
		when(userPanelProxy.getAccountInfo()).thenReturn(userResponse);
		when(forecastServiceProxy.getDailyForecast(dayOfMonth, cityResponse.getLat(), cityResponse.getLon())).thenReturn(dailyForecast);
		Model model = new ExtendedModelMap();
		
		String response = calendarService.getDailyForecast(dayOfMonth, planRequest, model);
		
		assertThat(response).isNotEmpty();
		assertThat(model.getAttribute("city")).isEqualTo(cityResponse);
		assertThat(model.getAttribute("plan")).isEqualTo(planRequest);
		assertThat(model.getAttribute("dailyForecast")).isEqualTo(dailyForecast);
	}

}
