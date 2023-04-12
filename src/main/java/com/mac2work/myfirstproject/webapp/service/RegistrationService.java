package com.mac2work.myfirstproject.webapp.service;

import org.springframework.stereotype.Service;

import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.model.UserDto;

@Service
public class RegistrationService {
	private final JpaUserDetailsService jpaUsersDetailService;
	
	
	
	public RegistrationService(JpaUserDetailsService jpaUsersDetailService) {
		this.jpaUsersDetailService = jpaUsersDetailService;
	}



	public boolean register(UserDto userDto) {
		
		return jpaUsersDetailService.signUpUser(
				new User(
						userDto.getUsername(),userDto.getPassword(), "ROLE_USER"));
	}

}
