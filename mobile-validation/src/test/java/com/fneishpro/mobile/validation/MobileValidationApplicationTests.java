package com.fneishpro.mobile.validation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fneishpro.mobile.validation.model.MobileInfo;
import com.fneishpro.mobile.validation.service.ValidateMobileService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableAutoConfiguration
class MobileValidationApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(MobileValidationApplicationTests.class);
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired private MockMvc mockMvc;
	@Autowired private ValidateMobileService validateMobileService;
	
	Random r = new Random();
	
	@Test
    @DisplayName("Test valid mobile")
	void deleteCustomerById() throws Exception {
		// get random customer
		String number = "+9613"+RandomStringUtils.random(6, false, true);
		
		// test the request to delete the customer by id
		MvcResult mvcResult = mockMvc.perform(get(new URI("/api/phone/validation/"+number)))
								.andExpect(status().isOk())
								.andReturn();
		
		String response = null;
		try {
			response = mvcResult.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		}
		
		if( response !=null ) {
			// test if the response is same customer i pick out above
			ObjectMapper mapper = new ObjectMapper();
			try {
				MobileInfo respMobileInfo = mapper.readValue(response, MobileInfo.class);
				if( respMobileInfo.isValid() ) {
					log.info("Mobile {} is valid, info: country name={}, country code={}, operator={}", 
							number, respMobileInfo.getCountryName(), respMobileInfo.getCountryCode(), respMobileInfo.getOperatorName());
				} else {
					log.info("Mobile {} is not valid", number);
				}
				
				// check if the response is equal if we use the service 
				MobileInfo mobileInfo = validateMobileService.getPhoneInfo(number);
				if( mobileInfo.isValid() == respMobileInfo.isValid() ) {
					boolean sameResult = false;
					if( mobileInfo.isValid() ) {
						sameResult = Objects.equals(mobileInfo.getCountryCode(), respMobileInfo.getCountryCode()) &&
								Objects.equals(mobileInfo.getCountryName(), respMobileInfo.getCountryName()) &&
								Objects.equals(mobileInfo.getOperatorName(), respMobileInfo.getOperatorName());
					} else {
						sameResult = true;
					}
					
					if( sameResult ) {
						log.info("Check mobile validation on number {} using call of the URL and the service are match", number);
					} else {
						log.info("Check mobile validation on number {} using call of the URL and the service are not match", number);
					}
				}
			} catch (JsonProcessingException e) {
				log.error("", e);
			}
		}
	}
}
