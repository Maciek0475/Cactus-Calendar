package com.mac2work.calendar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.cactus_library.model.Role;
import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.calendar.config.UserRepository;
import com.mac2work.calendar.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	

	public String register(UserRequest userRequest, BindingResult result, Model model) {
		if(userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
			model.addAttribute("error", "This username is already taken");
			return "register.html";
		}
		if(result.hasErrors())
			return "register.html";
		User user = User.builder()
				.username(userRequest.getUsername())
				.password(passwordEncoder.encode(userRequest.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.save(user);
		return "login.html";
	}

}
