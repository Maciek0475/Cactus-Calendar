package com.mac2work.forecast.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.mac2work.cactus_library.model.DailyForecast;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private ForecastService forecastService;
	
	private DailyForecast dailyForecast;
	private DailyForecast dailyForecast2;
	private double expectedSuccess;
	private double expectedSuccess2;
	private String apiCallResult;

	
	@BeforeEach
	void setUp() throws Exception {
		expectedSuccess = 40.0;
		expectedSuccess2 = 31.0;
		
		dailyForecast = DailyForecast.builder()
				.temp(17.5)
				.humidity(66.1)
				.date(LocalDate.parse("2024-10-01"))
				.build();
		dailyForecast2 = DailyForecast.builder()
				.temp(13.0)
				.humidity(63.0)
				.date(LocalDate.parse("2024-10-02"))
				.build();
		
		apiCallResult = """
				{
			"queryCost": 1,
			"latitude": 52.74,
			"longitude": 15.23,
			"resolvedAddress": "52.74,15.23",
			"address": "52.74,15.23",
			"timezone": "Europe/Warsaw",
			"tzoffset": 2,
			"days": [
			{
			"datetime": "2024-10-01",
			"temp": 17.5,
			"humidity": 66.1,
			"icon": "rain"
			},
			{
			"datetime": "2024-10-02",
			"temp": 13.0,
			"humidity": 63.0,
			"icon": "rain"
			}
			]
			}
				""";
		
	}

	@Test
	void forecastService_getForecast_ReturnMoreThanOneDailyForecastAndNullBefore() {
		LinkedList<DailyForecast> expectedDailyForecasts = new LinkedList<>();
		expectedDailyForecasts.add(dailyForecast);
		expectedDailyForecasts.add(dailyForecast2);
		expectedDailyForecasts.addFirst(null);
		DailyForecast calculatedDailyForecast = dailyForecast;
		calculatedDailyForecast.setSuccess(expectedSuccess);
		DailyForecast calculatedDailyForecast2 = dailyForecast2;
		calculatedDailyForecast.setSuccess(expectedSuccess2);
		ForecastService forecastServiceSpy = Mockito.spy(forecastService);
		when(restTemplate.getForObject("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/52.74,15.23?unitGroup=metric&elements=datetime,temp,humidity,icon&include=days&key=3XQ3BGCW8Y9TEGLCV9642PV7D&contentType=json", String.class)).thenReturn(apiCallResult);
		when(forecastServiceSpy.calculateSuccess(Mockito.any())).thenReturn(null, calculatedDailyForecast, calculatedDailyForecast2);

		LinkedList<DailyForecast> actualDailyForecasts = forecastServiceSpy.getForecast(52.74, 15.23);
		
		assertThat(actualDailyForecasts).isEqualTo(expectedDailyForecasts);
	}
	
	@Test
	void forecastService_calculateSuccess_ReturnDailyForecastWithExpectedSuccess() {		
		DailyForecast actualDailyForecast = forecastService.calculateSuccess(dailyForecast);
		
		assertThat(actualDailyForecast.getSuccess()).isEqualTo(expectedSuccess);
	}
	
	@Test
	void forecastService_getDailyForecast_ReturnRequestedDailyForecast() {
		Double lat = 52.74;
		Double lon = 15.23;
		DailyForecast calculatedDailyForecast = dailyForecast;
		calculatedDailyForecast.setSuccess(expectedSuccess);
		DailyForecast calculatedDailyForecast2 = dailyForecast2;
		calculatedDailyForecast.setSuccess(expectedSuccess2);
		LinkedList<DailyForecast> dailyForecasts = new LinkedList<>();
		dailyForecasts.add(null);
		dailyForecasts.add(calculatedDailyForecast);
		dailyForecasts.add(calculatedDailyForecast2);
		ForecastService forecastServiceSpy = Mockito.spy(forecastService);
		doReturn(dailyForecasts).when(forecastServiceSpy).getForecast(lat, lon);
		
		DailyForecast actualDailyForecast = forecastServiceSpy.getDailyForecast(1, lat, lon);
		
		assertThat(actualDailyForecast).isEqualTo(calculatedDailyForecast);
	}

}
