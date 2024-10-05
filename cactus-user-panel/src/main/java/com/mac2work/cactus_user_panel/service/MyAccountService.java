package com.mac2work.cactus_user_panel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.cactus_user_panel.model.City;
import com.mac2work.cactus_user_panel.model.User;
import com.mac2work.cactus_user_panel.repository.CityRepository;
import com.mac2work.cactus_user_panel.repository.UserRepository;

import lombok.NoArgsConstructor;
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

	public UserResponse setCity(String username, Long id) {
		User user = getLoggedUser(username);
		City city = cityRepository.findById(id).orElseThrow();
		user.setCity(city);
		user = userRepository.save(user);
		return mapToUserResponse(user);
	}

	public User getLoggedUser(String username) {
			User user = userRepository.findByUsername(username).orElseThrow();
		return user;

	}

	public UserResponse getAccountInfo(String username) {
		User user = getLoggedUser(username);
		return mapToUserResponse(user);
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.username(user.getUsername())
				.cityResponse(user.getCity() != null? mapToCityResponse(user.getCity()) : null)
				.build();
	}

	private CityResponse mapToCityResponse(City city) {
		return CityResponse.builder()
				.id(city.getId())
				.name(city.getName())
				.lat(city.getLat())
				.lon(city.getLon())
				.build();
	}

	public Long getCityId(Long userId) {
		City city = userRepository.findById(userId).orElseThrow().getCity();
		return city.getId();
	}
	
	public CityResponse getCityById(Long cityId) {
		City city = cityRepository.findById(cityId).orElseThrow();
		return mapToCityResponse(city);
	}
}
