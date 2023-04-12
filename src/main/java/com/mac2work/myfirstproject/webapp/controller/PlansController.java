package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.service.PlanService;

@Controller
@RequestMapping("/plans")
public class PlansController {
	private final PlanService planService;

	public PlansController(PlanService planService) {
		this.planService = planService;
	}

	@GetMapping
	public String showPlans(Model model) {

		model.addAttribute("plans", planService.filterByDoneStatus(planService.findAllByUser(), false));
		return "plans.html";
	}

	@GetMapping("/remove")
	public String deletePlan(@RequestParam Long id) {
		planService.deletePlanById(id);
		return "redirect:/plans";
	}
}
