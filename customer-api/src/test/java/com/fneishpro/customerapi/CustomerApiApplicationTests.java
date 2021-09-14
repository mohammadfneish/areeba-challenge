package com.fneishpro.customerapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fneishpro.customerapi.model.Customer;
import com.fneishpro.customerapi.service.CustomerService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableAutoConfiguration
class CustomerApiApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerApiApplicationTests.class);
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired private MockMvc mockMvc;
	@Autowired private CustomerService customerService;
	
	Random r = new Random();
	
	/**
	 * get random customer from DB
	 * @return any random from DB or empty customer in case DB is empty
	 */
	private Customer getRandomCustomer() {
		Customer c = new Customer();
		try {
			List<Customer> customers = customerService.getAllCustomers("");
			if( customers!=null && !customers.isEmpty() ) {
				int indx = r.nextInt(customers.size());
				if( indx>customers.size() ) {
					indx = customers.size();
				} else if ( indx<=0 ) {
					indx = 1;
				}
				
				c = customers.get(indx-1);
			}
		} catch(Exception e) {
			log.warn("", e);
		}
		return c;
	}
	
	/**
	 * check if the given customer is equal to the response one
	 * @param mvcResult
	 * @param customer
	 * @return true if equal otherwise false
	 */
	private boolean checkCustomerFromResponse(MvcResult mvcResult, Customer customer) {
		String response;
		try {
			response = mvcResult.getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
			return false;
		}
		
		// test if the response is same customer i pick out above
		ObjectMapper mapper = new ObjectMapper();
		try {
			Customer respCustomer = mapper.readValue(response, Customer.class);
			return customer.getId()>0 ? respCustomer.getId()==customer.getId() : customer.getName().equalsIgnoreCase(respCustomer.getName());
		} catch (JsonProcessingException e) {
			log.error("", e);
			return false;
		}
	}
	
	@Test
    @DisplayName("Test get all customers")
	void getAllCustomers() throws Exception {
		mockMvc.perform(get(new URI("/api/customer/all")))
			.andExpect(status().isOk());
	}
	
	@Test
    @DisplayName("Test get customer by id")
	void getCustomerById() throws Exception {
		// get random customer
		Customer customer = getRandomCustomer();
		
		// test the request to get the customer by id
		MvcResult mvcResult = mockMvc.perform(get(new URI("/api/customer/"+customer.getId())))
								.andExpect(status().isOk())
								.andReturn();
		
		if( checkCustomerFromResponse(mvcResult, customer) ) {
			log.info("Random customer of ID={} is same as requested customer by id", customer.getId());
		} else {
			log.warn("Random customer of ID={} doesn't match the requested customer by id", customer.getId());
		}
	}
	
	@Test
	@DisplayName("Test add new customer")
	void addNewCustomer() throws Exception {
		// create random customer info
		Customer customer = new Customer();
		customer.setName("customer-"+RandomStringUtils.random(10, true, false));
		customer.setAddress("address-"+RandomStringUtils.random(10, true, false));
		customer.setMobile("+9613"+RandomStringUtils.random(6, false, true));
		
		// convert the customer info into json to able to send it in the body of the request
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(customer);
	    
	    // call add new customer
		MvcResult mvcResult = mockMvc.perform(
								post(new URI("/api/customer/"))
								.contentType(APPLICATION_JSON_UTF8)
						        .content(requestJson)
					        )
							.andExpect(status().isOk())
							.andReturn();
		
		// check the result
		if( checkCustomerFromResponse(mvcResult, customer) ) {
			log.info("The customer of Name={} is same as the created customer", customer.getName());
		} else {
			log.warn("The customer of Name={} doesn't match the created customer", customer.getName());
		}
	}

	@Test
	@DisplayName("Test update customer")
	void updateCustomer() throws Exception {
		Customer customer = getRandomCustomer();
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(customer);

	    // call update new customer
	    MvcResult mvcResult = mockMvc.perform(
						put(new URI("/api/customer/"+customer.getId()))
						.contentType(APPLICATION_JSON_UTF8)
				        .content(requestJson)
			        )
					.andExpect(status().isOk())
					.andReturn();
	    
	    // check the result
	    if( checkCustomerFromResponse(mvcResult, customer) ) {
			log.info("Random customer of ID={} is same as the updated customer", customer.getId());
		} else {
			log.warn("Random customer of ID={} doesn't match the updated customer", customer.getId());
		}
	}
	
	@Test
    @DisplayName("Test delete customer by id")
	void deleteCustomerById() throws Exception {
		// get random customer
		Customer customer = getRandomCustomer();
		
		// test the request to delete the customer by id
		mockMvc.perform(delete(new URI("/api/customer/"+customer.getId())))
								.andExpect(status().isOk());
	}
}
