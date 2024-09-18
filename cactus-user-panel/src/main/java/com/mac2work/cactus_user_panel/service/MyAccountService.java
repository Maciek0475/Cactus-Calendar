package com.mac2work.cactus_user_panel.service;

import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.cactus_user_panel.model.City;
import com.mac2work.cactus_user_panel.model.User;
import com.mac2work.cactus_user_panel.repository.CityRepository;
import com.mac2work.cactus_user_panel.repository.UserRepository;
import com.mac2work.cactus_user_panel.response.CityResponse;
import com.mac2work.cactus_user_panel.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyAccountService {

	private final CityRepository cityRepository;
	private final UserRepository userRepository;

	public List<CityResponse> getCities() {
		List<CityResponse> cities = cityRepository.findAll()
				.stream().map(city -> mapToCityResponse(city)).toList();
		return cities;
	}

	public UserResponse setCity(Long id) {
		User user = getLoggedUser();
		City city = cityRepository.findById(id).orElseThrow();
		user.setCity(city);
		user = userRepository.save(user);
		return mapToUserResponse(user);
	}

	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			user = userRepository.findByUsername(currentUsername).get();
		}
		return user;

	}

	public UserResponse getAccountInfo() {
		User user = getLoggedUser();
		return mapToUserResponse(user);
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.username(user.getUsername())
				.role(user.getRole())
				.city(mapToCityResponse(user.getCity()))
				.build();
	}

	private CityResponse mapToCityResponse(City city) {
		return CityResponse.builder()
				.name(city.getName())
				.lat(city.getLat())
				.lon(city.getLon())
				.build();
	}
}
