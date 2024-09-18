package com.mac2work.plans.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="CACTUS-USER-PANEL")
public interface UserPanelProxy {
	@GetMapping("")
	public Long getUserId();

	@GetMapping("")
	public Long getCityId();
}
