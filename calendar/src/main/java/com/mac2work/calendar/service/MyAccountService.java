package com.mac2work.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.calendar.proxy.UserPanelProxy;
import com.mac2work.calendar.response.CityResponse;
import com.mac2work.calendar.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyAccountService {
	private final UserPanelProxy userPanelProxy;

	public List<CityResponse> getCities() {
		return userPanelProxy.getCities();
	}

	public String setCity(Long cityId) {
		userPanelProxy.setAccountCity(cityId);
		return "redirect:/my-account";
	}

	public String getAccountInfo(Model model, Long cityId) {
		UserResponse userResponse =  userPanelProxy.getAccountInfo();
		model.addAttribute("user", userResponse);
		model.addAttribute("cities", getCities());
		model.addAttribute("cityId", cityId);
		return "my-account.html";
	}
}
