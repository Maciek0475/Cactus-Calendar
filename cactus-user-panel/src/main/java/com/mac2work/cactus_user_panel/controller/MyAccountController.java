package com.mac2work.cactus_user_panel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.service.MyAccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/my-account")
@RequiredArgsConstructor
public class MyAccountController {
	private final MyAccountService myAccountService;

	@GetMapping
	public String getAccountInfo(Model model, City city) {
		return myAccountService.getAccountInfo(model, city);
	}
	
	@PostMapping("/choose-city")
	public String setAccountCity(@ModelAttribute City city) {
		return myAccountService.setCity(city);
	}
}
