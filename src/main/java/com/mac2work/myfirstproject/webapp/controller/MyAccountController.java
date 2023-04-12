package com.mac2work.myfirstproject.webapp.controller;

import java.security.Principal;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.service.MyAccountService;

@Controller
@RequestMapping("/my-account")
public class MyAccountController {
	private final MyAccountService myAccountService;
	
	
	
	public MyAccountController(MyAccountService myAccountService) {
		this.myAccountService = myAccountService;
	} 



	@GetMapping
	public String getAccountInfo(Model model, City city) {
		model.addAttribute("user", myAccountService.getLoggedUser());
		model.addAttribute("cities", myAccountService.getCities());
		model.addAttribute("city", city);
		return "my-account.html";
	}
	
	@PostMapping("/choose-city")
	public String setAccountCity(@ModelAttribute City city) {
		myAccountService.setCity(city);
		return "redirect:/my-account";
	}

}
