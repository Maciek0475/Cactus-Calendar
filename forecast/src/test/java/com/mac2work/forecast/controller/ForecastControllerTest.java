package com.mac2work.forecast.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mac2work.forecast.service.ForecastService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ForecastController.class)
class ForecastControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ForecastService forecastService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void forecastController_getForecast_ReturnMoreThanOneDailyForecast() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void forecastController_getDailyForecast_ReturnDailyForecast() {
		fail("Not yet implemented"); // TODO
	}

}
