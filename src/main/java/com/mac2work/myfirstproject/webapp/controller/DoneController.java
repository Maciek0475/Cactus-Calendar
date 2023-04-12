package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.repository.PlanRepository;
import com.mac2work.myfirstproject.webapp.service.PlanService;

@Controller
@RequestMapping("/done")
public class DoneController {
	private final PlanRepository planRepository;
	private final PlanService planService;
	
	
	public DoneController(PlanRepository planRepository, PlanService planService) {
		this.planRepository = planRepository;
		this.planService = planService;
	}



	@GetMapping
	public String getDonePlans(Model model) {
		model.addAttribute("plans", planService.filterByDoneStatus(planService.findAllByUser(), true));
		return "done.html";
	}
	
	@GetMapping("/remove")
	public String deletePlan(@RequestParam Long id) {
		planService.deletePlanById(id);
		return "redirect:/done";
	}

}
