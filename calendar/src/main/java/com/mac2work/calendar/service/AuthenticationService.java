package com.mac2work.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.calendar.proxy.UserPanelProxy;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserPanelProxy userPanelProxy;
	
	private Model hasErrors(boolean hasErrors, Model model) {
		if(hasErrors)
			model.addAttribute("error", "Invalid data, try again");
		return model;
	}

	public String register(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		model = hasErrors(result.hasErrors(), model);
		if(userPanelProxy.findByUsername(userRequest.getUsername()))
			model.addAttribute("error", "This username is already taken");
		else {
			userPanelProxy.registerNewUser(userRequest);
			return "login.html";
		}
		return "register.html";
	}
	
	public String login(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		if(result.hasErrors()) {
		model = hasErrors(result.hasErrors(), model);
		}
		String token = userPanelProxy.login(userRequest);
		if(token != null) {
		    response.addHeader("Authorization", "Bearer:" + token);
		    return "/content";
		}
		model.addAttribute("error", "Invalid data!");
		return "/login";
			
	}



}
