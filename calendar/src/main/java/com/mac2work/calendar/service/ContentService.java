package com.mac2work.calendar.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
	private final UserRepository userRepository;

	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			user = userRepository.findByUsername(currentUsername).get();
		}
		return user;
	}
	
	public String getPlansCount(Model model, boolean isDone) {
		User user = getLoggedUser();
		long planCount = user.getPlans().stream().filter(p -> p.isDone() == isDone).count();
		model.addAttribute("user", user);
		model.addAttribute("undonePlansCount", planCount);
		return "content.html";
	}
}
