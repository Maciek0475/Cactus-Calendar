package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mac2work.myfirstproject.webapp.model.UserDto;
import com.mac2work.myfirstproject.webapp.service.RegistrationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private RegistrationService registrationService;
	
	
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@GetMapping
	public String getRegisterPage(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "register.html";
	}

	@PostMapping
	public String registerNewUser(
			@Valid @ModelAttribute UserDto userDto, BindingResult result, Model model) {
		if (result.hasErrors())
		    return "/register";
		
		boolean registerSuccess = registrationService.register(userDto);
		if(registerSuccess) {
			return "/login";
		}else {
				model.addAttribute("error", "This username is already taken");
		return "/register";
		}
	}

}
