package com.mac2work.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.calendar.proxy.UserPanelProxy;
import com.mac2work.calendar.request.UserRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserPanelProxy userPanelProxy;
	
	public Model hasErrors(boolean hasErrors, Model model) {
		if(hasErrors)
			model.addAttribute("error", "Invalid data, try again");
		return model;
	}

	public String register(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		model = hasErrors(result.hasErrors(), model);
		if(userPanelProxy.findByUsername(userRequest.getUsername()))
			model.addAttribute("error", "This username is already taken");
		else {
			userPanelProxy.registerNewUser(userRequest.getUsername(), userRequest.getPassword());
			return "/login";
		}
		return "/register";
	}
	
	public String login(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		if(result.hasErrors()) {
		model = hasErrors(result.hasErrors(), model);
		}
		else if(userPanelProxy.login(userRequest.getUsername(), userRequest.getPassword())) {
		    response.addHeader("Authorization", "Bearer:"+loginToken());
		    return "/content";
		}
		model.addAttribute("error", "Invalid data!");
		return "/login";
			
	}

	public String loginToken() {
		return userPanelProxy.getLoginToken();
	}



}
