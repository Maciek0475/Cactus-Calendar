package com.mac2work.myfirstproject.webapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mac2work.myfirstproject.webapp.model.Plan;
import com.mac2work.myfirstproject.webapp.repository.PlanRepository;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

@Service
@Transactional
public class PlanService {

	private final PlanRepository planRepository;
	private final UserRepository userRepository;
	private final CalendarService calendarService;

	public PlanService(PlanRepository planRepository, UserRepository userRepository, CalendarService calendarService) {
		this.planRepository = planRepository;
		this.userRepository = userRepository;
		this.calendarService = calendarService;
	}

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
	
	public List<Plan> filterByDoneStatus(List<Plan> plans, boolean doneStatus){
		plans = plans.stream().filter(p -> p.isDone() == doneStatus).toList();
		return plans;
	}

	public void save(Plan plan) {

		planRepository.save(plan);

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
		for (Plan plan : plans) {
			if (plan.getDate().isBefore(LocalDate.now()))
				planRepository.updateIsDoneById(plan.getId(), true);
			else {
				calendarService.forecast(plan.getCity().getLat(), plan.getCity().getLon());
				planRepository.updateSuccessPropabilityById(plan.getId(),
						calendarService.getDailyForecast(plan.getDate().getDayOfMonth()).getSuccess());
			}
			
		}
		
		

	}

}