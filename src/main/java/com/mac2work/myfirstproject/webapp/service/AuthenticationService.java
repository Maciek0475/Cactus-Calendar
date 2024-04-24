package com.mac2work.myfirstproject.webapp.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.myfirstproject.webapp.request.UserRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtService jwtService;

	public Model hasErrors(BindingResult result, Model model) {
		if(result.hasErrors())
			model.addAttribute("error", "This username is already taken");
		return model;
	}
	
	public Model hasErrors(boolean loginError, Model model) {
		if(loginError)
			model.addAttribute("error", "Invalid data, try again");
		return model;
	}

	public String register(@Valid UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public String login() {
		// TODO Auto-generated method stub
		return null;
	}



}
