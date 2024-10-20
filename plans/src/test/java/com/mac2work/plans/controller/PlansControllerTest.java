package com.mac2work.plans.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.plans.response.PlansResponse;
import com.mac2work.plans.service.PlansService;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PlansController.class)
class PlansControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PlansService plansService;
	
	private PlanResponse planResponse;
	private PlanRequest planRequest;
	private PlansResponse plansResponse;
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
		plansResponse = PlansResponse.builder()
				.isSuccess(true)
				.message("Plan deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
		
		planRequest = PlanRequest.builder()
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.build();
	}

	@Test
	void PlansController_getPlansByDoneStatus_ReturnDonePlans() throws Exception {
		String userId = String.valueOf(planResponse.getUserId());
		boolean doneStatus = planResponse.isDone();
		List<PlanResponse> planResponses = List.of(planResponse);
		when(plansService.getPlansByDoneStatus(userId, doneStatus)).thenReturn(planResponses);
		ResultActions result = mockMvc.perform(get("/api/plans/" + doneStatus)
				.header("LoggedUserId", userId)
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.size()", CoreMatchers.is(planResponses.size())));
	}

	@Test
	void PlansController_saveNewPlan_ReturnPlanResponse() throws Exception {
		String userId = String.valueOf(planResponse.getUserId());
		when(plansService.saveNewPlan(userId, planRequest)).thenReturn(planResponse);
		ResultActions result = mockMvc.perform(post("/api/plans")
				.header("LoggedUserId", userId)
				.content(objectMapper.writeValueAsString(planRequest))
				.contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(planResponse.getId()), Object.class))))
			.andExpect(jsonPath("$.date", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(planResponse.getDate()), Object.class))))
			.andExpect(jsonPath("$.note", CoreMatchers.is(planResponse.getNote())))
			.andExpect(jsonPath("$.successPropability", CoreMatchers.is(planResponse.getSuccessPropability())))
			.andExpect(jsonPath("$.cityResponse", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(planResponse.getCityResponse()), Object.class))))
			.andExpect(jsonPath("$.done", CoreMatchers.is(planResponse.isDone())))
			.andExpect(jsonPath("$.userId", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(planResponse.getId()), Object.class))));	
		}

	@Test
	void PlansController_deletePlan_ReturnPlansResponse() throws Exception {
		when(plansService.deletePlan(planResponse.getId())).thenReturn(plansResponse);
		
		ResultActions result = mockMvc.perform(delete("/api/plans/" +  planResponse.getId())
				.contentType(MediaType.APPLICATION_JSON));
			
		result.andExpect(jsonPath("$.isSuccess", CoreMatchers.is(plansResponse.getIsSuccess())))
			.andExpect(jsonPath("$.message", CoreMatchers.is(plansResponse.getMessage())))
			.andExpect(jsonPath("$.httpStatus", CoreMatchers.is(objectMapper.readValue(objectMapper.writeValueAsString(plansResponse.getHttpStatus()), Object.class))));
	}

}
