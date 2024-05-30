package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.model.Plan;
import com.mac2work.myfirstproject.webapp.service.CalendarService;
import com.mac2work.myfirstproject.webapp.service.PlanService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

	private final CalendarService calendarService;
	private final PlanService planService;

	@GetMapping
	public String buildCalendar(Model model) {
		return calendarService.forecast(model);
	}
	
	@GetMapping("/new-plan")
	public String planNewPlan( @RequestParam("month") int month, Plan plan, Model model) {
		return calendarService.getDailyForecast(month, plan, model);
	}
	
	@PostMapping("/new-plan")
	public String saveNewPlan(@ModelAttribute Plan plan) {
		return planService.saveNewPlan(plan);
	}

}
