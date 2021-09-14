/**
 * 
 */
package com.fneishpro.mobile.validation.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fneishpro.mobile.validation.model.MobileInfo;
import com.fneishpro.mobile.validation.service.ValidateMobileService;

/**
 * @author M.Fneish
 *
 */

@RestController
@RequestMapping("/api/phone/validation")
public class ValidateMobileController {

	@Autowired private ValidateMobileService validateMobileService;
	
	@GetMapping("/{number}")
	public ResponseEntity<MobileInfo> getPhoneInfo (
			@PathVariable String number) throws JSONException {
		
		MobileInfo mobileInfo = validateMobileService.getPhoneInfo(number);
		
		return new ResponseEntity<MobileInfo>(mobileInfo, HttpStatus.OK);
	}
}
