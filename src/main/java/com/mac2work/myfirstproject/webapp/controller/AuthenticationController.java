package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.request.UserRequest;
import com.mac2work.myfirstproject.webapp.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@GetMapping("/register")
	public String getRegisterPage(UserRequest userRequest, Model model) {
		model.addAttribute("userRequest", userRequest);
		return "register.html";
	}

	@PostMapping("/register")
	public String registerNewUser(@Valid @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {
		return authenticationService.register(userRequest, result, model);
	}

	@PostMapping("/login")
	public String logIn(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
		return authenticationService.login(loginError, model);
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout.html";
	}
	
	
	
}
