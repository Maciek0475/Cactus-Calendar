package com.mac2work.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;
import com.mac2work.calendar.proxy.UserPanelProxy;
import com.mac2work.calendar.request.CityId;

@ExtendWith(MockitoExtension.class)
class MyAccountServiceTest {

	@Mock
	private UserPanelProxy userPanelProxy;
	
	@InjectMocks
	private MyAccountService myAccountService;
	
	private CityResponse cityResponse;
	private CityResponse cityResponse2;
	private UserResponse userResponse;
	private CityId cityId;
	
	@BeforeEach
	void setUp() throws Exception {
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
		userResponse = UserResponse.builder()
				.username("mac2work@gmail.com")
				.cityResponse(cityResponse)
				.planResponses(null)
				.build();
		cityId = CityId.builder()
				.id(cityResponse.getId())
				.build();

	}

	@Test
	void myAccountService_getCities_ReturnCityResponses() {
		List<CityResponse> cityResponses = List.of(cityResponse, cityResponse2);
		when(userPanelProxy.getCities()).thenReturn(cityResponses);
		
		List<CityResponse> actualCityResponses = myAccountService.getCities();
		
		assertThat(actualCityResponses).isEqualTo(cityResponses);
 	}

	@Test
	void myAccountService_setCity_ReturnRedirectEndpoint() {
		CityId cityId = CityId.builder().id(cityResponse.getId()).build();
		doNothing().when(userPanelProxy).setAccountCity(cityResponse.getId());
		
		String redirect = myAccountService.setCity(cityId);
		
		assertThat(redirect).isNotEmpty();
	}

	@Test
	void myAccountService_getAccountInfo_CheckIfModelIsCorrectAndReturnRedirectEndpoint() {
		Model model = new ExtendedModelMap();
		MyAccountService myAccountServiceSpy = Mockito.spy(myAccountService);
		List<CityResponse> cityResponses = List.of(cityResponse, cityResponse2);
		when(userPanelProxy.getAccountInfo()).thenReturn(userResponse);
		doReturn(cityResponses).when(myAccountServiceSpy).getCities();
		
		String response = myAccountServiceSpy.getAccountInfo(model, cityId);
		
		assertThat(response).isNotEmpty();
		assertThat(model.getAttribute("user")).isEqualTo(userResponse);
		assertThat(model.getAttribute("cities")).isEqualTo(cityResponses);
		assertThat(model.getAttribute("cityId")).isEqualTo(cityId);
		
	}

}
