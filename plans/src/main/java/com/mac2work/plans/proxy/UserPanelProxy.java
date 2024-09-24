package com.mac2work.plans.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="CACTUS-USER-PANEL")
public interface UserPanelProxy {
	@GetMapping("/api/panel/my-account/get-city-id/{userId}")
	public Long getCityId(@PathVariable Long userId);
}
