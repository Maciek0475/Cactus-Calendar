package com.mac2work.calendar.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mac2work.calendar.response.CityResponse;
import com.mac2work.calendar.response.UserResponse;

@FeignClient(name="USER-PANEL")
public interface UserPanelProxy {

	@GetMapping("/api/my-account/cities")
	List<CityResponse> getCities();

	@PatchMapping("/api/my-account/set-city/{id}")
	void setAccountCity(@PathVariable Long cityId);

	@GetMapping("/api/my-account")
	UserResponse getAccountInfo();

}
