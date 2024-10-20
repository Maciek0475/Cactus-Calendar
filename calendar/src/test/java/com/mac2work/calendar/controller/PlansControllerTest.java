package com.mac2work.calendar.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.calendar.service.PlanService;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PlansController.class)
class PlansControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PlanService planService;

	private PlanResponse planResponse;
	private CityResponse cityResponse;
	@BeforeEach
	void setUp() throws Exception {
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
	void plansController_showPlans_CheckModelAttributesAndReturnFileName() throws Exception {
		when(planService.filterByDoneStatus(Mockito.any(), Mockito.anyBoolean())).thenReturn("plans.html");
	
		ResultActions result = mockMvc.perform(get("/calendar/plans").flashAttr("plans", planResponse)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("plans.html"))
			.andExpect(model().attribute("plans", planResponse));
	}	
			

	@Test
	void plansController_deletePlan_ReturnRedirect() throws Exception {
		String id = String.valueOf(planResponse.getId());
		doNothing().when(planService).deletePlanById(planResponse.getId());
		
		ResultActions result = mockMvc.perform(get("/calendar/plans/remove").param("id", id)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(view().name("redirect:/calendar/plans"));
	}

}
