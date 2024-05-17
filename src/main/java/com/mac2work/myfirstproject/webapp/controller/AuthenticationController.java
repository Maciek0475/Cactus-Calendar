package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mac2work.myfirstproject.webapp.request.UserRequest;
import com.mac2work.myfirstproject.webapp.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("userRequest", new UserRequest());
		return "register.html";
	}

	@PostMapping("/register")
	public String registerNewUser(@Valid  UserRequest userRequest, BindingResult result, Model model) {
		return authenticationService.register(userRequest, result, model);
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}
	@PostMapping("/login")
	public String logIn(BindingResult result, Model model) {
		return authenticationService.login(result, model);
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout.html";
	}
	
	
	
}
