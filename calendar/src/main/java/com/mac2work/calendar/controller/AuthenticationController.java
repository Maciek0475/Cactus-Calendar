package com.mac2work.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.calendar.service.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/calendar/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("userRequest", new UserRequest());
		return "register.html";
	}

	@PostMapping("/register")
	public String registerNewUser(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		return authenticationService.register(userRequest, result, model, response);
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(UserRequest userRequest, BindingResult result, Model model, HttpServletResponse response) {
		return authenticationService.login(userRequest, result, model, response);
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout.html";
	}
	
	
	
}
