package com.mac2work.calendar.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mac2work.calendar.response.CityResponse;
import com.mac2work.calendar.response.UserResponse;

@FeignClient(name="USER-PANEL")
public interface UserPanelProxy {

	@GetMapping("/api/panel/my-account/cities")
	List<CityResponse> getCities();

	@PatchMapping("/api/panel/my-account/set-city/{id}")
	void setAccountCity(@PathVariable Long cityId);

	@GetMapping("/api/panel/my-account")
	UserResponse getAccountInfo();

	@PostMapping("/api/panel/auth/register")
	void registerNewUser(String username, String password);

	@GetMapping("/api/panel/user/find")
	Boolean findByUsername(String username);

	@GetMapping("/api/panle/auth/login-token")
	String getLoginToken();

	@PostMapping("/api/panel/auth/login")
	Boolean login(String username, String password);

}
