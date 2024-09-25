package com.mac2work.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.proxy.UserPanelProxy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
	private final UserPanelProxy userPanelProxy;
	
	public String getPlansCount(Model model, boolean isDone) {
		System.out.println();
		UserResponse userResponse = userPanelProxy.getAccountInfo();
		Long planCount = 0L;
		if(userResponse.getPlanResponses() != null)
		planCount = userResponse.getPlanResponses().stream().filter(plan -> plan.isDone() == false).count();
		String username = userResponse.getUsername();
		userResponse.setUsername(username.substring(0, username.indexOf('@')));
		model.addAttribute("user", userResponse);
		model.addAttribute("undonePlansCount", planCount);
		return "content.html";
	}
}
