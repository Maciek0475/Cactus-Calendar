package com.mac2work.cactus_user_panel.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.myfirstproject.webapp.config.JwtProvider;
import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;
import com.mac2work.myfirstproject.webapp.request.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtProvider jwtProvider;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	
	
	public Model hasErrors(boolean hasErrors, Model model) {
		if(hasErrors)
			model.addAttribute("error", "Invalid data, try again");
		return model;
	}

	public String register(UserRequest userRequest, BindingResult result, Model model) {
		model = hasErrors(result.hasErrors(), model);
		if(userRepository.findByUsername(userRequest.getUsername()).isPresent())
			model.addAttribute("error", "This username is already taken");
		else if(!result.hasErrors()) {
			User user = User.builder()
						.username(userRequest.getUsername())
						.password(passwordEncoder.encode(userRequest.getPassword()))
						.role(userRequest.getRole())
						.build();
			userRepository.save(user);
			return "/login";
		}
		return "/register";
	}

	public String loginToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = String.valueOf(auth.getName());
		jwtProvider.generateToken(username);
		return "redirect:/content";
	}



}
