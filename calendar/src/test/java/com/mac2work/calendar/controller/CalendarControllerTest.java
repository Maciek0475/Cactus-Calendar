package com.mac2work.calendar.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

import com.mac2work.cactus_library.exception.ResourceNotFoundException;
import com.mac2work.cactus_library.model.DailyForecast;
import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.calendar.service.CalendarService;
import com.mac2work.calendar.service.PlanService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CalendarController.class)
class CalendarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CalendarService calendarService;
	@MockBean
	private PlanService planService;
	
	private DailyForecast dailyForecast;
	private DailyForecast dailyForecast2;
	private CityResponse cityResponse;
	private PlanRequest planRequest;
	private ResourceNotFoundException exception;
	private String invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		invalidId = "2";
		exception = new ResourceNotFoundException("Plan", "id", invalidId);
		
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
		planRequest = PlanRequest.builder()
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.build();
	}

	@Test
	void calendarController_buildCalendar_CheckModelAttributesAndReturnFileName() throws Exception {
		boolean isCityChosen = true;
		List<DailyForecast> dailyForecasts = List.of(dailyForecast, dailyForecast2);
		Map<String, Object> attrs = Map.of("isCityChosen", isCityChosen, "dailyForecasts", dailyForecasts);
		when(calendarService.forecast(Mockito.any())).thenReturn("calendar.html");
		
		ResultActions result = mockMvc.perform(get("/calendar")
				.flashAttrs(attrs)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("calendar.html"))
			.andExpect(model().attribute("isCityChosen", isCityChosen))
			.andExpect(model().attribute("dailyForecasts", dailyForecasts));	
	}
	
	@Test
	void calendarController_buildCalendar_ReturnErrorView() throws Exception {
		when(calendarService.forecast(Mockito.any())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(get("/calendar")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("custom-error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));	
	}

	@Test
	void calendarController_planNewPlan_CheckModelAttributesAndReturnFileName() throws Exception {
		Map<String, Object> attrs = Map.of("city", cityResponse, "plan", planRequest, "dailyForecast", dailyForecast);
		when(calendarService.getDailyForecast(Mockito.anyInt(), Mockito.any(), Mockito.any())).thenReturn("new-plan.html");
		
		ResultActions result = mockMvc.perform(get("/calendar/new-plan")
				.param("month", String.valueOf(dailyForecast.getDate().getDayOfMonth())).flashAttrs(attrs)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("new-plan.html"))
			.andExpect(model().attribute("city", cityResponse))
			.andExpect(model().attribute("plan", planRequest))
			.andExpect(model().attribute("dailyForecast", dailyForecast));
	}
	
	@Test
	void calendarController_planNewPlan_ReturnErrorView() throws Exception {
		when(calendarService.getDailyForecast(Mockito.anyInt(), Mockito.any(), Mockito.any())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(get("/calendar/new-plan")
				.param("month", String.valueOf(dailyForecast.getDate().getDayOfMonth()))
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("custom-error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));	
	}

	@Test
	void calendarController_saveNewPlan_ReturnFileName() throws Exception {
		when(planService.saveNewPlan(Mockito.any())).thenReturn("new-plan-success.html");
		
		ResultActions result = mockMvc.perform(post("/calendar/new-plan")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("new-plan-success.html"));
	}
	
	@Test
	void calendarController_saveNewPlan_ReturnErrorView() throws Exception {
		when(planService.saveNewPlan(Mockito.any())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(post("/calendar/new-plan")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("custom-error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));	
	}

}
