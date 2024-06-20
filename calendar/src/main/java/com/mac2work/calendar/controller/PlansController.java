package com.mac2work.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.calendar.service.PlanService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlansController {
	private final PlanService planService;

	@GetMapping
	public String showPlans(Model model) {
		return planService.filterByDoneStatus(model, false);
	}

	@GetMapping("/remove")
	public String deletePlan(@RequestParam Long id) {
		planService.deletePlanById(id);
		return "redirect:/plans";
	}
}
