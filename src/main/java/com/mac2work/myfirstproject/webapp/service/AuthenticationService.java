package com.mac2work.myfirstproject.webapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.myfirstproject.webapp.config.JwtProvider;
import com.mac2work.myfirstproject.webapp.model.Role;
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

	public Model hasErrors(BindingResult result, Model model) {
		if(result.hasErrors())
			model.addAttribute("error", "This username is already taken");
		return model;
	}
	
	public Model hasErrors(boolean loginError, Model model) {
		if(loginError)
			model.addAttribute("error", "Invalid data, try again");
		return model;
	}

	public String register(UserRequest userRequest, BindingResult result, Model model) {
		model = hasErrors(result.hasErrors(), model);
		if(userRepository.findByUsername(userRequest.getUsername()).isPresent())
			model.addAttribute("error", "This username is already taken");
		else {
			User user = User.builder()
						.username(userRequest.getUsername())
						.password(passwordEncoder.encode(userRequest.getPassword()))
						.role(Role.USER)
						.build();
			userRepository.save(user);
			return "/login";
		}
		return "/register";
	}

	public String login(boolean loginError, Model model) {
		model = hasErrors(loginError, model);
		String username;
		if(!loginError) {
			username = String.valueOf(model.getAttribute("username"));
			jwtProvider.generateToken(username);
		}
		return "/login";
	}



}
