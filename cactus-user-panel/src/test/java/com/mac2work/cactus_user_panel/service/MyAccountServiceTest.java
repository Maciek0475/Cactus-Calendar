package com.mac2work.cactus_user_panel.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mac2work.cactus_library.model.Role;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.cactus_user_panel.model.City;
import com.mac2work.cactus_user_panel.model.User;
import com.mac2work.cactus_user_panel.repository.CityRepository;
import com.mac2work.cactus_user_panel.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class MyAccountServiceTest {
	@Mock
	private CityRepository cityRepository;
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private MyAccountService myAccountService;
	
	private City city;
	private City city2;
	private CityResponse cityResponse;
	private CityResponse cityResponse2;
	private User user;
	private User updatedUser;
	private UserResponse userResponse;
	
	@BeforeEach
	void setUp() throws Exception {
		
		city = City.builder()
				.id(1L)
				.name("Gorzów Wielkopolski")
				.lat(52.74)
				.lon(15.23)
				.build();
		city2 = City.builder()
				.id(2L)
				.name("Szczecin")
				.lat(53.43)
				.lon(14.529)
				.build();
		
		cityResponse = CityResponse.builder()
				.name("Gorzów Wielkopolski")
				.id(1L)
				.lat(52.74)
				.lon(15.23)
				.build();
		cityResponse2 = CityResponse.builder()
				.id(2L)
				.name("Szczecin")
				.lat(53.43)
				.lon(14.529)
				.build();
		
		user = User.builder()
				.username("mac2work@gmail.com")
				.password("P@ssword123")
				.role(Role.USER)
				.city(city)
				.build();
		updatedUser = User.builder()
				.username("mac2work@gmail.com")
				.password("P@ssword123")
				.role(Role.USER)
				.city(city2)
				.build();
		
		userResponse = UserResponse.builder()
				.username("mac2work@gmail.com")
				.cityResponse(cityResponse)
				.planResponses(null)
				.build();
	}

	@Test
	void myAccountService_getCities_ReturnMoreThanOneCityResponse() {
		List<City> cities = List.of(city, city2);
		List<CityResponse> cityResponses = List.of(cityResponse, cityResponse2);
		when(cityRepository.findAll()).thenReturn(cities);
		
		List<CityResponse> actualCityResponses = myAccountService.getCities();
		
		assertThat(actualCityResponses).isEqualTo(cityResponses);
	}

	@Test
	void myAccountService_setCity_ReturnUserResponseWithChangedCity() {
		
		MyAccountService myAccountServiceSpy = Mockito.spy(myAccountService);
		doReturn(user).when(myAccountServiceSpy).getLoggedUser(user.getUsername());
		when(cityRepository.findById(city2.getId())).thenReturn(Optional.of(city2));
		when(userRepository.save(updatedUser)).thenReturn(updatedUser);
		
		UserResponse actualUserResponse = myAccountServiceSpy.setCity(user.getUsername(), city2.getId());
		
		assertThat(actualUserResponse.getCityResponse()).isEqualTo(cityResponse2);
	}

	@Test
	void myAccountService_getLoggedUser_ReturnUser() {
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		
		User actualUser = myAccountService.getLoggedUser(user.getUsername());
		
		assertThat(actualUser).isEqualTo(user);
	}

	@Test
	void myAccountService_getAccountInfo_ReturnUserResponse() {
		MyAccountService myAccountServiceSpy = Mockito.spy(myAccountService);
		doReturn(user).when(myAccountServiceSpy).getLoggedUser(user.getUsername());
		
		UserResponse actualUserResponse = myAccountServiceSpy.getAccountInfo(user.getUsername());
		
		assertThat(actualUserResponse).isEqualTo(userResponse);
	}

	@Test
	void myAccountService_getCityId_ReturnUsersCityId() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		Long cityId = myAccountService.getCityId(user.getId());
		
		assertThat(cityId).isEqualTo(user.getCity().getId());
	}

	@Test
	void myAccountService_getCityById_ReturnCity() {
		when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
		
		CityResponse actualCityResponse = myAccountService.getCityById(city.getId());
		
		assertThat(actualCityResponse).isEqualTo(cityResponse);
	}

}
