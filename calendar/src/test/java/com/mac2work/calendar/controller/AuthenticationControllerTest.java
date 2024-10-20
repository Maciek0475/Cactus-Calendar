package com.mac2work.calendar.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.calendar.service.AuthenticationService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationService authenticationService;

	@Test
	void authenticationController_getRegisterPage_CheckModelAttributesAndReturnFileName() throws Exception {	
		UserRequest userRequest = new UserRequest();
		ResultActions result = mockMvc.perform(get("/calendar/auth/register")
				.flashAttr("userRequest", userRequest)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("register.html"))
			.andExpect(model().attribute("userRequest", userRequest));
	}

	@Test
	void authenticationController_registerNewUser_CheckModelAttributesAndReturnFileName() throws Exception {
		when(authenticationService.register(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("login.html");
		
		ResultActions result = mockMvc.perform(post("/calendar/auth/register")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("login.html"))
			.andExpect(model().attributeDoesNotExist("error"));
	}

	@Test
	void authenticationController_getLoginPage_ReturnFileName() throws Exception {
		ResultActions result = mockMvc.perform(get("/calendar/auth/login"));
		
		result.andExpect(view().name("login.html"));	
	}

	@Test
	void authenticationController_getLogoutPage_ReturnFileName() throws Exception {
		ResultActions result = mockMvc.perform(get("/calendar/auth/logout"));
		
		result.andExpect(view().name("logout.html"));	
	}

}
