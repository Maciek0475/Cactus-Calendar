package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.model.UserDto;
import com.mac2work.myfirstproject.webapp.request.UserRequest;
import com.mac2work.myfirstproject.webapp.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@GetMapping("/register")
	public String getRegisterPage(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "register.html";
	}

	@PostMapping("/register")
	public String registerNewUser(@Valid @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {
//		if (result.hasErrors())
//		    return "/register";
//		
//		boolean registerSuccess = authenticationService.register(userDto);
//		if(registerSuccess) {
//			return "/login";
//		}else {
//				model.addAttribute("error", "This username is already taken");
//		return "/register";
//		}
		model = authenticationService.hasErrors(result, model);
		return authenticationService.register(userRequest);
	}

	@PostMapping("/login")
	public String logIn(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
//		if(loginError) {
//			model.addAttribute("error", "Invalid data, try again");
//		}
//		return "login.html";
		model = authenticationService.hasErrors(loginError, model);
		return authenticationService.login();
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout.html";
	}
	
	
	
}
