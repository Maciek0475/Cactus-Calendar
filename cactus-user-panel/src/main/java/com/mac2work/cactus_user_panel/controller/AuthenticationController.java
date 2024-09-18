package com.mac2work.cactus_user_panel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.cactus_user_panel.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/panel/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public boolean registerNewUser(@RequestBody UserRequest userRequest) {
		return authenticationService.register(userRequest);
	}

	@PostMapping("/login")
	public String login(@RequestBody UserRequest userRequest) {
		return authenticationService.login(userRequest);
	}
//	@GetMapping("/login-token")
//	public String loginToken() {
//		return authenticationService.loginToken();
//	}
	@GetMapping("/find/{username}")
	public Boolean findByUsername(@PathVariable String username) {
		 return authenticationService.findByUsername(username);
	}

	@GetMapping("/logout")
	public String getLogoutPage() {
		return "logout.html";
	}
	
	
	
}
