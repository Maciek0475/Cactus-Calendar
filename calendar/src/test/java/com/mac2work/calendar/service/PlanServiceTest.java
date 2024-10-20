package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.calendar.proxy.PlansServiceProxy;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

	@Mock
	private PlansServiceProxy plansServiceProxy;
	
	@InjectMocks
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
	void planService_filterByDoneStatus_ReturnCorrectHtmlFileName() {
		List<PlanResponse> planResponses = List.of(planResponse);
		when(plansServiceProxy.getPlansByDoneStatus(planResponse.isDone())).thenReturn(planResponses);
		Model model = new ExtendedModelMap();
		
		String response = planService.filterByDoneStatus(model, planResponse.isDone());
				
		assertThat(response).isEqualTo("done.html");
		assertThat(model.getAttribute("plans")).isEqualTo(planResponses);
	}

}
