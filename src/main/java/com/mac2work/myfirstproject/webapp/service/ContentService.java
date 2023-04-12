package com.mac2work.myfirstproject.webapp.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

@Service
public class ContentService {
	
	private final UserRepository userRepository;
	
	

	public ContentService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}



	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			user = userRepository.findByUsername(currentUsername).get();
		}
		return user;

	}
	public long getPlansCount(boolean isDone) {
		long planCount = getLoggedUser().getPlans().stream().filter(p -> p.isDone() == isDone).count();
		return planCount;
	}
}
