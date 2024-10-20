package com.mac2work.cactus_user_panel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.cactus_user_panel.service.MyAccountService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MyAccountController.class)
class MyAccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private MyAccountService myAccountService;
	
	
	private CityResponse cityResponse;
	private CityResponse cityResponse2;
	private UserResponse userResponse;

	@BeforeEach
	void setUp() throws Exception {
		cityResponse = CityResponse.builder()
				.name("Gorzów Wielkopolski")
				.id(1L)
				.lat(52.74)
				.lon(15.23)
				.build();
		cityResponse2 = CityResponse.builder()
				.id(2L)
				.name("Szczecin")
				.lat(53.43)
				.lon(14.529)
				.build();
		userResponse = UserResponse.builder()
				.username("mac2work@gmail.com")
				.cityResponse(cityResponse)
				.planResponses(null)
				.build();
	}

	@Test
	void myAccountController_getAccountInfo_ReturnUserResponse() throws Exception {
		when(myAccountService.getAccountInfo(userResponse.getUsername())).thenReturn(userResponse);
		
		ResultActions result = mockMvc.perform(get("/api/panel/my-account")
				.header("LoggedUsername", userResponse.getUsername())
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.username", CoreMatchers.is(userResponse.getUsername())))
			.andExpect(jsonPath("$.password", CoreMatchers.is(userResponse.getPassword())))
			.andExpect(jsonPath("$.cityResponse", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(userResponse.getCityResponse()), Object.class))));
	}

	@Test
	void myAccountController_getCities_ReturnMoreThanOneCityResponse() throws Exception {
		List<CityResponse> cityResponses = List.of(cityResponse, cityResponse2);
		when(myAccountService.getCities()).thenReturn(cityResponses);
		
		ResultActions result = mockMvc.perform(get("/api/panel/my-account/cities")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.size()", CoreMatchers.is(cityResponses.size())));
			
		
	}

	@Test
	void myAccountController_setAccountCity_ReturnUserResponse() throws Exception {
		userResponse.setCityResponse(cityResponse2);
		when(myAccountService.setCity(userResponse.getUsername(), cityResponse2.getId())).thenReturn(userResponse);
		
		ResultActions result = mockMvc.perform(put("/api/panel/my-account/set-city")
				.header("LoggedUsername", userResponse.getUsername())
				.content(String.valueOf(2L))
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.username", CoreMatchers.is(userResponse.getUsername())))
			.andExpect(jsonPath("$.password", CoreMatchers.is(userResponse.getPassword())))
			.andExpect(jsonPath("$.cityResponse", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(userResponse.getCityResponse()), Object.class))));
	}

	@Test
	void myAccountController_getCityId_ReturnCityId() throws Exception {
		when(myAccountService.getCityId(1L)).thenReturn(cityResponse.getId());
		
		ResultActions result = mockMvc.perform(get("/api/panel/my-account/get-city-id/1")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(cityResponse.getId()), Object.class))));
	}

	@Test
	void myAccountController_getCityById_ReturnCityResponse() throws Exception {
		when(myAccountService.getCityById(cityResponse.getId())).thenReturn(cityResponse);
		
		ResultActions result = mockMvc.perform(get("/api/panel/my-account/get-city/1")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(cityResponse.getId()), Object.class))))
			.andExpect(jsonPath("$.name", CoreMatchers.is(cityResponse.getName())))
			.andExpect(jsonPath("$.lat", CoreMatchers.is(cityResponse.getLat())))
			.andExpect(jsonPath("$.lon", CoreMatchers.is(cityResponse.getLon())));
	}

}
