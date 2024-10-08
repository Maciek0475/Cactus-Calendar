package com.mac2work.plans.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.mac2work.cactus_library.model.DailyForecast;
import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.plans.model.Plan;
import com.mac2work.plans.proxy.ForecastServiceProxy;
import com.mac2work.plans.proxy.UserPanelProxy;
import com.mac2work.plans.repository.PlanRepository;
import com.mac2work.plans.response.PlansResponse;

@ExtendWith(MockitoExtension.class)
class PlansServiceTest {
	
	@Mock
	private UserPanelProxy userPanelProxy;
	@Mock
	private ForecastServiceProxy forecastServiceProxy;
	@Mock
	private PlanRepository planRepository;
	
	@InjectMocks
	private PlansService plansService;
	
	private CityResponse cityResponse;
	private Plan plan;
	private Plan plan2;
	private PlanResponse planResponse;
	private PlanRequest planRequest;
	private PlansResponse plansResponse;
	private DailyForecast dailyForecast2;
	
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
				.isDone(false)
				.userId(1L)
				.build();
		
		planRequest = PlanRequest.builder()
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.build();
		
		plan = Plan.builder()
				.id(1L)
				.date(LocalDate.parse("2024-10-05"))
				.note("Great day for planting")
				.successPropability(80.0)
				.cityId(1L)
				.isDone(false)
				.userId(1L)
				.build();
		plan2 = Plan.builder()
				.id(2L)
				.date(LocalDate.parse("2024-10-06"))
				.note("50 - 50")
				.successPropability(50.0)
				.cityId(1L)
				.isDone(false)
				.userId(1L)
				.build();
		plansResponse = PlansResponse.builder()
				.isSuccess(true)
				.message("Plan deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
		dailyForecast2 = DailyForecast.builder()
				.success(45.0)
				.build();
	}

	@Test
	void plansService_getPlansByDoneStatus_ReturnUndonePlans() {
		String userId = String.valueOf(planResponse.getUserId());
		List<Plan> plans = List.of(plan);
		List<PlanResponse> planResponses = List.of(planResponse);
		PlansService plansServiceSpy = Mockito.spy(plansService);
		doReturn(plans).when(plansServiceSpy).findAllByUserId(userId);
		when(userPanelProxy.getCityById(cityResponse.getId())).thenReturn(cityResponse);
		
		List<PlanResponse> actualPlanResponses = plansServiceSpy.getPlansByDoneStatus(userId , planResponse.isDone());
		
		assertThat(actualPlanResponses).isEqualTo(planResponses);
	}

	@Test
	void plansService_deletePlan_ReturnPlansResponse() {
		when(planRepository.findById(plan.getId())).thenReturn(Optional.of(plan));
		doNothing().when(planRepository).deleteById(plan.getId());
		
		PlansResponse actualPlansReponse = plansService.deletePlan(plan.getId());
		
		assertThat(actualPlansReponse).isEqualTo(plansResponse);
	}

	@Test
	void plansService_findAllByUserId_ReturnListOfPlans() {
		List<Plan> plans = List.of(plan, plan2);
		when(planRepository.findAllByUserId(plan.getUserId())).thenReturn(plans);
		PlansService plansServiceSpy = Mockito.spy(plansService);
		doReturn(plans).when(plansServiceSpy).updatePlans(plans);
		
		List<Plan> actualPlans = plansServiceSpy.findAllByUserId( String.valueOf(plan.getUserId()));
		
		assertThat(actualPlans).isEqualTo(plans);
	}

	@SuppressWarnings("unchecked")
	@Test
	void plansService_updatePlans_ReturnUpdatedPlans() {
		LocalDate currentLocalDate = LocalDate.of(2024, 10, 06);
        MockedStatic<LocalDate> topDateTimeUtilMock = Mockito.mockStatic(LocalDate.class);
        topDateTimeUtilMock.when(() -> LocalDate.now()).thenReturn(currentLocalDate);
		List<Plan> plans = List.of(plan, plan2);
		doNothing().when(planRepository).updateIsDoneById(plan.getId(), true);
		when(userPanelProxy.getCityById(plan.getCityId())).thenReturn(cityResponse);
		doNothing().when(planRepository).updateSuccessPropabilityById(plan2.getId(), dailyForecast2.getSuccess());
		when(forecastServiceProxy.getDailyForecast(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(dailyForecast2);
		plan.setDone(true);
		plan2.setSuccessPropability(dailyForecast2.getSuccess());
		when(planRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(plan), Optional.of(plan2));
		
		List<Plan> actualPlans = plansService.updatePlans(plans);
		
		assertThat(actualPlans).isEqualTo(plans);
	}
	

	@Test
	void plansService_saveNewPlan_ReturnPlanResponse() {
		when(userPanelProxy.getCityId(plan.getUserId())).thenReturn(cityResponse.getId());
		when(planRepository.save(Mockito.any())).thenReturn(plan);
		when(userPanelProxy.getCityById(cityResponse.getId())).thenReturn(cityResponse);
		
		PlanResponse actualPlanResponse = plansService.saveNewPlan(String.valueOf(plan.getUserId()), planRequest);
		
		assertThat(actualPlanResponse).isEqualTo(planResponse);
	}

}
