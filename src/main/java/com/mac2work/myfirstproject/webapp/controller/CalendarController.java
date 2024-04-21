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

@Controller
@RequestMapping("/calendar")
public class CalendarController {

	private final CalendarService calendarService;
	private final PlanService planService;

	public CalendarController(CalendarService calendarService, PlanService planService) {
		this.calendarService = calendarService;
		this.planService = planService;
	}

	@GetMapping
	public String buildCalendar(Model model) {
		boolean isCityChosen = calendarService.getLoggedUser().getCity() != null;
		model.addAttribute("isCityChosen", isCityChosen);
		if(isCityChosen)
		model.addAttribute("dailyForecasts", calendarService.forecast());
		return "calendar.html";
	}
	
	@GetMapping("/new-plan")
	public String planNewPlan( @RequestParam("month") int month, Plan plan, Model model) {
		model.addAttribute("user", calendarService.getLoggedUser());
		model.addAttribute("plan", plan);
		model.addAttribute("dailyForecast", calendarService.getDailyForecast(month));
		return "new-plan.html";
	}
	
	@PostMapping("/new-plan")
	public String saveNewPlan(@ModelAttribute Plan plan) {
		System.out.println(plan);
		planService.assignUser(plan);
		planService.save(plan);
		return "new-plan-success.html";
	}

}
