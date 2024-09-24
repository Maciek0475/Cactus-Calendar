package com.mac2work.cactus_user_panel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.cactus_user_panel.service.MyAccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/panel/my-account")
@RequiredArgsConstructor
public class MyAccountController {
	private final MyAccountService myAccountService;

	@GetMapping
	public UserResponse getAccountInfo(@RequestHeader("LoggedUsername") String username) {
		return myAccountService.getAccountInfo(username);
	}
	
	@GetMapping("/cities")
	public List<CityResponse> getCities() {
		return myAccountService.getCities();
	}
	
	@PutMapping("/set-city")
	public UserResponse setAccountCity(@RequestHeader("LoggedUsername") String username, @RequestBody Long id) {
		return myAccountService.setCity(username, id);
	}
	@GetMapping("/get-city-id/{userId}")
	public Long getCityId(@PathVariable Long userId) {
		return myAccountService.getCityId(userId);
	}
}
