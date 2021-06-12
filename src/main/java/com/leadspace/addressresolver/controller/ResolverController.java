package com.leadspace.addressresolver.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
 * That class is a collection of the application rest API 
 * @author sncohen
 *
 */
@RestController
public class ResolverController {
	
	 @Autowired AddressValidatorService addressValidatorService;
	 
	 @RequestMapping("/")
	    public ResponseEntity<Object> index() {
	    	return new ResponseEntity<Object>(HttpStatus.OK);
	    }
	    

	 /**
	  * Rest API for validating an specified address 
	  * @param address
	  * @return
	  * @throws Exception
	  */
	    @PostMapping(value = "/address/resolv", produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody
	    public Resolve resolvAddress(@RequestBody Address address) throws Exception  {
	    	
	    	return addressValidatorService.validateAddress(address);
	            
	    }
	    

}
