package com.mac2work.calendar.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mac2work.calendar.proxy.UserPanelProxy;
import com.mac2work.calendar.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
	private final UserPanelProxy userPanelProxy;
	
	public String getPlansCount(Model model, boolean isDone) {
		UserResponse userResponse = userPanelProxy.getAccountInfo();
		Long planCount = userResponse.getPlanResponses().stream().filter(plan -> plan.isDone() == true).count();
		model.addAttribute("user", userResponse);
		model.addAttribute("undonePlansCount", planCount);
		return "content.html";
	}
}
