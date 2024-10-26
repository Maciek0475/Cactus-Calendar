package com.mac2work.calendar.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

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
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.calendar.service.PlanService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = DoneController.class)
class DoneControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlanService planService;

	private PlanResponse planResponse;
	private CityResponse cityResponse;
	private ResourceNotFoundException exception;
	private String invalidId;
	
	@BeforeEach
	void setUp() throws Exception {
		invalidId = "2";
		exception = new ResourceNotFoundException("Plan", "id", invalidId);
		
		cityResponse = CityResponse.builder()
				.name("Gorzów Wielkopolski")
				.id(1L)
				.lat(52.74)
				.lon(15.23)
				.build();
		planResponse = PlanResponse.builder()
				.id(1L)
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.cityResponse(cityResponse)
				.isDone(true)
				.userId(1L)
				.build();
	}

	@Test
	void doneController_getDonePlans_CheckModelAttributesAndReturnFileName() throws Exception {
		when(planService.filterByDoneStatus(Mockito.any(), Mockito.anyBoolean())).thenReturn("plans.html");
		
		ResultActions result = mockMvc.perform(get("/calendar/done").flashAttr("plans", planResponse)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("plans.html"))
			.andExpect(model().attribute("plans", planResponse));
	}
	
	@Test
	void doneController_getDonePlans_ReturnErrorView() throws Exception {
		when(planService.filterByDoneStatus(Mockito.any(), Mockito.anyBoolean())).thenThrow(exception);
		
		ResultActions result = mockMvc.perform(get("/calendar/done")
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));
	}

	@Test
	void doneController_deletePlan_ReturnRedirect() throws Exception {
		String id = String.valueOf(planResponse.getId());
		doNothing().when(planService).deletePlanById(planResponse.getId());
		
		ResultActions result = mockMvc.perform(get("/calendar/done/remove").param("id", id)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("redirect:/calendar/done"));
	}
	
	@Test
	void doneController_deletePlan_ReturnErrorView() throws Exception {
		doThrow(exception).when(planService).deletePlanById(Long.valueOf(invalidId));

		ResultActions result = mockMvc.perform(get("/calendar/done/remove").param("id", invalidId)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("error"))
			.andExpect(r -> assertTrue(r.getResolvedException() instanceof ResourceNotFoundException))
			.andExpect(model().attribute("exception", exception));
	}

}
