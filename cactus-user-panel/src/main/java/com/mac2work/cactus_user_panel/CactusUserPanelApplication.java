package com.mac2work.cactus_user_panel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CactusUserPanelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CactusUserPanelApplication.class, args);
	}

}
