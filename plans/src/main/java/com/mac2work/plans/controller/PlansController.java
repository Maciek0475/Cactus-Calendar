package com.mac2work.plans.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.plans.response.PlanResponse;
import com.mac2work.plans.response.PlansResponse;
import com.mac2work.plans.service.PlansService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlansController {
	private final PlansService plansService;
	
	@GetMapping("/{status}")
	public List<PlanResponse> getPlansByDoneStatus(@PathVariable Boolean status){
		List<PlanResponse> planResponses = plansService.getPlansByDoneStatus(status);
		return planResponses;
	}
	
	@PostMapping("/{date}/{note}/{successPropability}")
	public PlanResponse saveNewPlan(@PathVariable LocalDate date, @PathVariable String note, @PathVariable Double successPropability) {
		PlanResponse planResponse = plansService.saveNewPlan(date, note, successPropability);
		return planResponse;
	}

	@DeleteMapping("/{id}")
	public PlansResponse deletePlan(@PathVariable Long id){
		PlansResponse plansResponse = plansService.deletePlan(id);
		return plansResponse;
	}
}
