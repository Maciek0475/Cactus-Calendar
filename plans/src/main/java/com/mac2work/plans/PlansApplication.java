package com.mac2work.plans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlansApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlansApplication.class, args);
	}

}
