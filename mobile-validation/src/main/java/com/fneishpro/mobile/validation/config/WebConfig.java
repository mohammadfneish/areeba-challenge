/**
 * 
 */
package com.fneishpro.mobile.validation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fneish
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * enable the CROS domain origin
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
        		.allowedOrigins("*")
        		.allowedMethods("*")
        		.allowCredentials(false).maxAge(3600);
    }
	
}
