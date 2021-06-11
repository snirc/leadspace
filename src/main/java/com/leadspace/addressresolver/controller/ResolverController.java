package com.leadspace.addressresolver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.leadspace.addressresolver.pojo.Address;
import com.leadspace.addressresolver.pojo.Resolve;
import com.leadspace.addressresolver.services.AddressValidatorService;

/**
 * 
 * @author sncohen
 *
 */
@RestController
public class ResolverController {

	 @RequestMapping("/")
	    public ResponseEntity<Object> index() {
	    	return new ResponseEntity<Object>(HttpStatus.OK);
	    }
	    

	    @PostMapping(value = "/address/resolv", produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public Resolve resolvAddress(@RequestBody Address address) throws Exception  {
	    	AddressValidatorService addressValidatorService = new AddressValidatorService();
	    	
	    	return addressValidatorService.validateAddress(address);
	            
	    }
	    

}
