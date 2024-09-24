package com.mac2work.calendar.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mac2work.cactus_library.request.UserRequest;
import com.mac2work.cactus_library.response.CityResponse;
import com.mac2work.cactus_library.response.UserResponse;

@FeignClient(name="CACTUS-USER-PANEL", configuration = {UserPanelFeignConfig.class})
public interface UserPanelProxy {

	@GetMapping("/api/panel/my-account/cities")
	public List<CityResponse> getCities();

	@PutMapping("/api/panel/my-account/set-city")
	public void setAccountCity(@RequestBody Long cityId);

	@GetMapping("/api/panel/my-account")
	public UserResponse getAccountInfo();

	@PostMapping("/api/panel/auth/register")
	public boolean registerNewUser(@RequestBody UserRequest password);

	@GetMapping("/api/panel/auth/find/{username}")
	public Boolean findByUsername(@PathVariable String username);

	@GetMapping("/api/panel/auth/login-token")
	public String getLoginToken();

	@PostMapping("/api/panel/auth/login")
	public String login(@RequestBody UserRequest userRequest);

}
