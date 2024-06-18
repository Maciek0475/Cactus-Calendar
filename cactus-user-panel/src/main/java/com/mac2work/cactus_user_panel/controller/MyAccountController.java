package com.mac2work.cactus_user_panel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.cactus_user_panel.response.CityResponse;
import com.mac2work.cactus_user_panel.response.UserResponse;
import com.mac2work.cactus_user_panel.service.MyAccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/my-account")
@RequiredArgsConstructor
public class MyAccountController {
	private final MyAccountService myAccountService;

	@GetMapping
	public UserResponse getAccountInfo() {
		return myAccountService.getAccountInfo();
	}
	
	@GetMapping("/cities")
	public List<CityResponse> getCities() {
		return myAccountService.getCities();
	}
	
	@PatchMapping("/set-city/{id}")
	public UserResponse setAccountCity(@PathVariable Long id) {
		return myAccountService.setCity(id);
	}
}
