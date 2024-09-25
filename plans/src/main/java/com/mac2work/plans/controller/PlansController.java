package com.mac2work.plans.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.cactus_library.request.PlanRequest;
import com.mac2work.cactus_library.response.PlanResponse;
import com.mac2work.plans.response.PlansResponse;
import com.mac2work.plans.service.PlansService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlansController {
	private final PlansService plansService;
	
	@GetMapping("/{status}")
	public List<PlanResponse> getPlansByDoneStatus(@RequestHeader("LoggedUserId") String userId, @PathVariable Boolean status){
		List<PlanResponse> planResponses = plansService.getPlansByDoneStatus(userId, status);
		return planResponses;
	}
	
	@PostMapping
	public PlanResponse saveNewPlan(@RequestHeader("LoggedUserId") String userId, @RequestBody PlanRequest planRequest) {
		PlanResponse planResponse = plansService.saveNewPlan(userId, planRequest);
		return planResponse;
	}

	@DeleteMapping("/{id}")
	public PlansResponse deletePlan(@PathVariable Long id){
		PlansResponse plansResponse = plansService.deletePlan(id);
		return plansResponse;
	}
	
}
