package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.calendar.config.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
	
	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private BindingResult bindingResult;
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
	private UserRequest userRequest;
	
	@BeforeEach
	void setUp() throws Exception {
		userRequest = UserRequest.builder()
				.username("mac2work@gmail.com")
				.password("Password123!")
				.build();
	}

	@Test
	void authenticationService_register_ReturnCorrectHtmlFileName() {
		Model model = new ExtendedModelMap();
		when(userRepository.findByUsername(userRequest.getUsername())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(userRequest.getPassword())).thenReturn(userRequest.getPassword());
		when(userRepository.save(Mockito.any())).thenReturn(null);
		
		String response = authenticationService.register(userRequest, bindingResult, model);
		
		String expectedResponse = "login.html";
		assertThat(response).isEqualTo(expectedResponse);
	}

}
