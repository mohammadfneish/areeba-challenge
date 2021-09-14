package com.fneishpro.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fneishpro.mobile.validation.service.ValidateMobileService;

@SpringBootApplication
@EnableAutoConfiguration
public class CustomerApiApplication {

	@Bean
	public ValidateMobileService validateMobileService() {
		return new ValidateMobileService();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerApiApplication.class, args);
	}

}
