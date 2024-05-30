package com.mac2work.myfirstproject.webapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.mac2work.myfirstproject.webapp.model.Plan;
import com.mac2work.myfirstproject.webapp.repository.PlanRepository;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

	private final PlanRepository planRepository;
	private final UserRepository userRepository;
	private final CalendarService calendarService;

	public List<Plan> findAllByUser() {
		List<Plan> plans = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			plans = userRepository.findByUsername(currentUsername).get().getPlans();
		}
		if(!plans.isEmpty())
		updatePlans(plans);
		return plans;
	}
	
	public String filterByDoneStatus(Model model, boolean doneStatus){
		List<Plan> plans = findAllByUser();
		plans = plans.stream().filter(p -> p.isDone() == doneStatus).toList();
		model.addAttribute("plans", plans);
		return doneStatus? "done.html" : "plans.html";
	}

	public void assignUser(Plan plan) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			plan.setUser(userRepository.findByUsername(currentUserName).get());
		}
	}

	public void deletePlanById(Long id) {
		planRepository.deleteById(id);
	}

	public void updatePlans(List<Plan> plans) {
		plans.stream().forEach(plan -> {
			if (plan.getDate().isBefore(LocalDate.now()))
				planRepository.updateIsDoneById(plan.getId(), true);
			else {
				planRepository.updateSuccessPropabilityById(plan.getId(),
				calendarService.getDailyForecast(plan.getDate().getDayOfMonth()).getSuccess());
			}
		});
	}

	public String saveNewPlan(Plan plan) {
		assignUser(plan);
		planRepository.save(plan);
		return "new-plan-success.html";
	}

}