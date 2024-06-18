package com.mac2work.plans.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mac2work.plans.model.Plan;
import com.mac2work.plans.proxy.CalendarServiceProxy;
import com.mac2work.plans.proxy.UserPanelProxy;
import com.mac2work.plans.repository.PlanRepository;
import com.mac2work.plans.response.PlanResponse;
import com.mac2work.plans.response.PlansResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlansService {
	private final UserPanelProxy userPanelProxy;
	private final CalendarServiceProxy calendarServiceProxy;
	private final PlanRepository planRepository;
	
	public List<PlanResponse> getPlansByDoneStatus(Boolean status) {
		List<Plan> plans = findAllByUserId();
		List<PlanResponse> planResponses = plans.stream().filter(p -> p.isDone() == status).map(plan -> 
			PlanResponse.builder()
			.date(plan.getDate())
			.note(plan.getNote())
			.successPropability(plan.getSuccessPropability())
			.cityId(plan.getCityId())
			.isDone(plan.isDone())
			.userId(plan.getUserId())
			.build()).toList();
		return planResponses;
	}
	
	public PlansResponse deletePlan(Long id) {
		planRepository.findById(id).orElseThrow();
		planRepository.deleteById(id);
		return PlansResponse.builder()
				.isSuccess(true)
				.message("Plan deleted successfully")
				.httpStatus(HttpStatus.OK)
				.build();
	}
	
	public List<Plan> findAllByUserId() {
		Long userId = userPanelProxy.getUserId();
		List<Plan> plans = planRepository.findAllByUserId(userId);
		plans = updatePlans(plans);
		return plans;
	}

	public List<Plan> updatePlans(List<Plan> plans) {
		plans.stream().forEach(plan -> {
			if (plan.getDate().isBefore(LocalDate.now()))
				plan = planRepository.updateIsDoneById(plan.getId(), true);
			else {
				plan = planRepository.updateSuccessPropabilityById(plan.getId(),
						calendarServiceProxy.getDailyForecastSuccess(plan.getDate().getDayOfMonth()));
			}
		});
		return plans;
	}

	public PlanResponse saveNewPlan(LocalDate date, String note, Double successPropability) {
		Long userId = userPanelProxy.getUserId();
		Long cityId = userPanelProxy.getCityId();
		Plan plan = Plan.builder()
				.date(date)
				.note(note)
				.successPropability(successPropability)
				.cityId(cityId)
				.isDone(false)
				.userId(userId)
				.build();
		plan = planRepository.save(plan);
		
		return PlanResponse.builder()
				.date(plan.getDate())
				.note(plan.getNote())
				.successPropability(plan.getSuccessPropability())
				.cityId(plan.getCityId())
				.isDone(plan.isDone())
				.userId(plan.getUserId())
				.build();
	}


}
