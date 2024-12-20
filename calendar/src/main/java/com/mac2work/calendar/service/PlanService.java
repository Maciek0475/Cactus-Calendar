package com.mac2work.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.calendar.proxy.PlansServiceProxy;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
	private final PlansServiceProxy plansServiceProxy;
	
	public String filterByDoneStatus(Model model, boolean doneStatus){
		List<PlanResponse> planResponses = plansServiceProxy.getPlansByDoneStatus(doneStatus);
		model.addAttribute("plans", planResponses);
		return doneStatus? "done.html" : "plans.html";
	}

	public void deletePlanById(Long id) {
		plansServiceProxy.deletePlan(id);
	}

	public String saveNewPlan(PlanRequest plan) {
		plansServiceProxy.saveNewPlan(plan);
		return "new-plan-success.html";
	}

}