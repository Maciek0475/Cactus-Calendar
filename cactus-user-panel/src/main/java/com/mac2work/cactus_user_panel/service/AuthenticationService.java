package com.mac2work.cactus_user_panel.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.cactus_user_panel.model.Role;
import com.mac2work.cactus_user_panel.model.User;
import com.mac2work.cactus_user_panel.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public boolean register(UserRequest userRequest) {
			User user = User.builder()
						.username(userRequest.getUsername())
						.password(passwordEncoder.encode(userRequest.getPassword()))
						.role(Role.USER)
						.build();
			userRepository.save(user);
			return true;
	}
	
	public String login(UserRequest userRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userRequest.getUsername(), 
						userRequest.getPassword()
						)
				);
		if(!findByUsername(userRequest.getUsername()))
			return null;
		return loginToken();
	}

	public Boolean findByUsername(String username) {
		try {
			userRepository.findByUsername(username).orElseThrow();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String loginToken() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = String.valueOf(auth.getName());
		return jwtService.generateToken(username);
	}



}
