package com.mac2work.myfirstproject.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mac2work.myfirstproject.webapp.model.City;
import com.mac2work.myfirstproject.webapp.model.User;
import com.mac2work.myfirstproject.webapp.repository.CityRepository;
import com.mac2work.myfirstproject.webapp.repository.UserRepository;

@Service
public class MyAccountService {

	private final CityRepository cityRepository;
	private final UserRepository userRepository;

	public MyAccountService(CityRepository cityRepository, UserRepository userRepository) {
		this.cityRepository = cityRepository;
		this.userRepository = userRepository;
	}

	public List<City> getCities() {
		List<City> cities = new ArrayList<>();
		cities = (List<City>) cityRepository.findAll();
		return cities;
	}

	public void setCity(City city) {

		city = cityRepository.findById(city.getId()).get();
		userRepository.UpdateCityIdByName(city, getLoggedUser().getUsername());

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
}
