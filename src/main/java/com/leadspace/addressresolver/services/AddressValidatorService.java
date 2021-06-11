package com.leadspace.addressresolver.services;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.leadspace.addressresolver.DefaultParas;
import com.leadspace.addressresolver.pojo.Address;
import com.leadspace.addressresolver.pojo.Resolve;

import io.micrometer.core.instrument.util.StringUtils;

public class AddressValidatorService {

	/**
	 * Validate the specified address and return a Resolve object
	 * @param address
	 * @return
	 */
	public Resolve validateAddress(Address address) {
		Resolve resolve = new Resolve();
		try {
		// Checks for missing fields in the Address objecct 
		if (!validateAddressFields(address)) 
			resolve.setStatus(DefaultParas.InsufficientLocationInput);
		
		else {
			
			JSONObject responseObject = getAddressInformationFromGeocode(address);
			resolve = validateResponseItems(responseObject);
			
		}		
			
		} catch (Exception e) {
			resolve.setStatus(DefaultParas.Invalid);
		}
		
		
		return resolve;
	}

	/**
	 * 
	 * @param responseObject
	 * @return
	 */
	public Resolve validateResponseItems(JSONObject responseObject) throws Exception{
		Resolve resolve = new Resolve();
		// Validate if it return an error
		if(responseObject.has("error")) {
			resolve.setStatus(DefaultParas.Unknown);
			resolve.setHereUsage(0);
		}else {
			JSONArray items = responseObject.getJSONArray("items");
			if(items.length() <1)
				resolve.setStatus(DefaultParas.Invalid);
			else {
				JSONObject itemObj = items.getJSONObject(0);
				itemObj = itemObj.getJSONObject("address");
				if(!itemObj.has("houseNumber"))
					resolve.setStatus(DefaultParas.Invalid);
				else {
					String[] notContainOnStreet = {"POBox", "Mailbox", "LockedBag"};
					
				    if (Arrays.stream(notContainOnStreet).anyMatch(itemObj.getString("street")::contains))
				    	resolve.setStatus(DefaultParas.Invalid);
				    else if( itemObj.has("houseNumber") && itemObj.has("houseNumber")) {
				    	resolve.setStatus(DefaultParas.Valid);
				    	resolve.setNormalized(itemObj.getString("label"));
				    }else 
				    	resolve.setStatus(DefaultParas.Invalid);
				    
				}
				
			}	
			resolve.setHereUsage(1);
		}
		return resolve;
	}

	/**
	 * 
	 * @param address
	 * @return
	 */
	public boolean validateAddressFields(Address address) {
		boolean isValid = false;

		isValid = StringUtils.isNotBlank(address.getCity()) && StringUtils.isNotBlank(address.getCountry())
				&& StringUtils.isNotBlank(address.getPostalCode()) && StringUtils.isNotBlank(address.getState())
				&& StringUtils.isNotBlank(address.getStreet());

		return isValid;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject getAddressInformationFromGeocode(Address address) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		StringBuilder queryBuilder = new StringBuilder("q=");
		queryBuilder.append(address.getStreet()).append("&").append(address.getStreet()).append("&")
				.append(address.getState()).append("&").append(address.getPostalCode()).append("&")
				.append(address.getCountry()).append("&apiKey=").append(DefaultParas.API_KEY);

		ResponseEntity<String> response = restTemplate.getForEntity(DefaultParas.GEO_URL + queryBuilder, String.class);

		JSONObject responseObject = new JSONObject(response.getBody());

		return responseObject;

	}
}
