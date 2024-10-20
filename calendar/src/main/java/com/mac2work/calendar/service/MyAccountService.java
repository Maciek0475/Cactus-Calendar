package com.mac2work.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.proxy.UserPanelProxy;
import com.mac2work.calendar.request.CityId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyAccountService {
	private final UserPanelProxy userPanelProxy;

	public List<CityResponse> getCities() {
		return userPanelProxy.getCities();
	}

	public String setCity(CityId cityId) {
		userPanelProxy.setAccountCity(cityId.getId());
		return "redirect:/calendar/my-account";
	}

	public String getAccountInfo(Model model, CityId cityId) {
		UserResponse userResponse =  userPanelProxy.getAccountInfo();
		model.addAttribute("user", userResponse);
		model.addAttribute("cities", getCities());
		model.addAttribute("cityId", cityId);
		return "my-account.html";
	}
}
