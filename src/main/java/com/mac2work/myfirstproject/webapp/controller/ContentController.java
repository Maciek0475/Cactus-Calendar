package com.mac2work.myfirstproject.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac2work.myfirstproject.webapp.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	private final ContentService contentService;

	public ContentController(ContentService contentService) {
		this.contentService = contentService;
	}
	@GetMapping
	public String getContentPage(Model model) {
		model.addAttribute("user", contentService.getLoggedUser());
		model.addAttribute("undonePlansCount", contentService.getPlansCount(false));
		return "content.html";
	}
	@GetMapping("/guest")
	public String getGuestPage() {
		return "guest-content.html";
	}
	

}
