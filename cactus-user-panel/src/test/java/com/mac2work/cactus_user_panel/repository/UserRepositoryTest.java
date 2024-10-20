package com.mac2work.cactus_user_panel.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mac2work.cactus_library.model.Role;
import com.mac2work.cactus_user_panel.model.City;
import com.mac2work.cactus_user_panel.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	
	private City city;
	private User user;

	@BeforeEach
	void setUp() throws Exception {
		city = City.builder()
				.name("Gorzów Wielkopolski")
				.lat(52.74)
				.lon(15.23)
				.build();
		cityRepository.save(city);
		
		user = User.builder()
				.username("mac2work@gmail.com")
				.password("P@ssword123")
				.role(Role.USER)
				.city(city)
				.build();
		userRepository.save(user);
	}

	@Test
	void userRepository_findByUsername_ReturnUser() {
		String username = "mac2work@gmail.com";
		
		User actualUser = userRepository.findByUsername(username).get();
		
		assertThat(actualUser).isEqualTo(user);
	}

}
