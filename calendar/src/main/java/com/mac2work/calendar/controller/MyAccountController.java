package com.mac2work.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.calendar.request.CityId;
import com.mac2work.calendar.service.MyAccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/calendar/my-account")
@RequiredArgsConstructor
public class MyAccountController {
	private final MyAccountService myAccountService;

	@GetMapping
	public String getAccountInfo(Model model, CityId cityId) {
		return myAccountService.getAccountInfo(model, cityId);
	}
	
	@PostMapping("/choose-city")
	public String setAccountCity(@ModelAttribute CityId cityId) {
		return myAccountService.setCity(cityId);
	}
}
