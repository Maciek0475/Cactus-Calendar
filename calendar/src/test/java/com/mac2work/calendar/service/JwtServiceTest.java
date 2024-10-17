package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {
	
	@InjectMocks
	private JwtService jwtService;

	private String token;
	private String username;
	
	@BeforeEach
	void setUp() throws Exception {
		token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWMyd29ya0BnbWFpbC5jb20iLCJpYXQiOjE3MjkxMTgxMjQsImV4cCI6MTcyOTE2MTMyNH0.sE_WuTDePGca9pwmBMTJErvwAsfsy7ZRSrrHN5ci01k";
		username = "mac2work@gmail.com";
		
	}

	@Test
	void jwtService_extractUsername_ReturnCorrectUsername() { 
		JwtService jwtServiceSpy = Mockito.spy(jwtService);
		doReturn(username).when(jwtServiceSpy).extractClaim(Mockito.anyString(), Mockito.any());
		
		String actualUsername = jwtServiceSpy.extractUsername(token);
		
		assertThat(actualUsername).isEqualTo(username);
	}

	@Test
	void jwtService_extractClaim_ReturnUsername() {
		String actualClaim = jwtService.extractClaim(token, Claims::getSubject);
		
		assertThat(actualClaim).isEqualTo(username);
	}

	@Test
	void jwtService_generateToken_ReturnToken() {
		String generatedToken = jwtService.generateToken(new HashMap<>(), username);
		
		assertThat(generatedToken).isNotEmpty();
	}

	@Test
	void jwtService_isTokenValid_ReturnFalse() {
		boolean expectedResponse = false;
		
		boolean isTokenValid = jwtService.isTokenValid(token, username);
		
		assertThat(isTokenValid).isEqualTo(expectedResponse);
	}

}
