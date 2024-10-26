package com.mac2work.calendar.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.request.CityId;
import com.mac2work.calendar.service.MyAccountService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MyAccountController.class)
class MyAccountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MyAccountService myAccountService;

	private CityResponse cityResponse;
	private CityResponse cityResponse2;
	private UserResponse userResponse;
	private CityId cityId;
	private Long invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		invalidId = -1L;
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
		cityId = CityId.builder()
				.id(cityResponse.getId())
				.build();
	}
	
	@Test
	void myAccountController_getAccountInfo_CheckModelAttributesAndReturnFileName() throws Exception {
		Map<String, Object> attrs = Map.of("user", userResponse, "cities", List.of(cityResponse, cityResponse2), "cityId", cityId);
		when(myAccountService.getAccountInfo(Mockito.any(), Mockito.any())).thenReturn("my-account.html");
		
		ResultActions result = mockMvc.perform(get("/calendar/my-account").flashAttrs(attrs)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("my-account.html"))
			.andExpect(model().attribute("user", userResponse))
			.andExpect(model().attribute("cities", List.of(cityResponse, cityResponse2)))
			.andExpect(model().attribute("cityId", cityId));
	}
	@Test
	void myAccountController_getAccountInfo_ReturnErrorView() throws Exception {
		ResourceNotFoundException exception = new ResourceNotFoundException("user", "id", invalidId);
		when(myAccountService.getAccountInfo(Mockito.any(), Mockito.any())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(get("/calendar/my-account")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));
	}

	@Test
	void myAccountController_setAccountCity_ReturnRedirect() throws Exception {
		when(myAccountService.setCity(Mockito.any())).thenReturn("redirect:/calendar/my-account");
		
		ResultActions result = mockMvc.perform(post("/calendar/my-account/choose-city")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("redirect:/calendar/my-account"));
	}
	
	@Test
	void myAccountController_setAccountCity_ReturnErrorView() throws Exception {
		ResourceNotFoundException exception = new ResourceNotFoundException("city", "id", invalidId);
		when(myAccountService.setCity(Mockito.any())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(post("/calendar/my-account/choose-city")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("error"))
		.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
		.andExpect(model().attribute("exception", exception));
	}

}
