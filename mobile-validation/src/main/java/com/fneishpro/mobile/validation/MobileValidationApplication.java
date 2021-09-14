package com.fneishpro.mobile.validation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MobileValidationApplication {

	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
	        		.allowedOriginPatterns("*")
	        		.allowedMethods("*")
	        		.allowCredentials(false).maxAge(3600);
			}
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MobileValidationApplication.class, args);
	}

}
