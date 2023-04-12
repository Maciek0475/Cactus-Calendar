package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {

	@RequestMapping("/login")
	public String logIn(@RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
		if(loginError) {
			model.addAttribute("error", "Invalid data, try again");
		}
		return "login.html";
	}
	
	
}
