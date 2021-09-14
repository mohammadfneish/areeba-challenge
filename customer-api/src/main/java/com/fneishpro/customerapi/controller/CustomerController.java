/**
 * 
 */
package com.fneishpro.customerapi.controller;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fneishpro.customerapi.model.Customer;
import com.fneishpro.customerapi.service.CustomerService;
import com.fneishpro.mobile.validation.model.MobileInfo;
import com.fneishpro.mobile.validation.service.ValidateMobileService;

/**
 * @author M.Fneish
 *
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired private CustomerService customerService;
	@Autowired private ValidateMobileService validateMobileService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/mobile/{number}")
	public ResponseEntity<MobileInfo> getPhoneInfo (
			@PathVariable String number) throws JSONException {
		
		MobileInfo mobileInfo = validateMobileService.getPhoneInfo(number);
		
		return new ResponseEntity<MobileInfo>(mobileInfo, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomers (
			@RequestParam(required = false) String queryText) {
		
		List<Customer> res = customerService.getAllCustomers(queryText);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(
			@PathVariable int id) {
		
		Customer res = customerService.getCustomerById(id);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Customer> createCustomer(
			@RequestBody Customer customer) {
		
		Customer res = customerService.createCustomer(customer);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(
			@PathVariable int id, 
			@RequestBody Customer customer) {
		
		Customer res = customerService.updateCustomer(customer);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteCustomer(
			@PathVariable int id) {
		
		boolean res = customerService.deleteCustomer(id);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
