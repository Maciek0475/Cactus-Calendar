package com.mac2work.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.myfirstproject.webapp.service.ContentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {
	private final ContentService contentService;

	@GetMapping
	public String getContentPage(Model model) {
		return contentService.getPlansCount(model, false);
	}
	
	@GetMapping("/guest")
	public String getGuestPage() {
		return "guest-content.html";
	}
	

}
