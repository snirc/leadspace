/**
 * 
 */
package com.leadspace.addressresolver.services;

import org.json.JSONObject;

import com.leadspace.addressresolver.DefaultParas;
import com.leadspace.addressresolver.pojo.Address;
import com.leadspace.addressresolver.pojo.Resolve;

import junit.framework.TestCase;

/**
 * @author sncohen
 *
 */
public class AddressValidatorServiceTest extends TestCase {

	AddressValidatorService addressValidatorService = new AddressValidatorService();
	Address address;
	
	protected void setUp() throws Exception {
		super.setUp();
		address = new Address();
		address.setCity("Paris");
		address.setCountry("France");
		address.setState("Île-de-France");
		address.setStreet("5 Rue Daunou");
		address.setPostalCode("75002");
	}

	/**
	 * Test method addressValidatorService.validateAddress
	 */
	public void testValidateAddress() {

		Resolve resolve = addressValidatorService.validateAddress(address);

		
		assertEquals(resolve.getStatus(), DefaultParas.Valid);

	}

	/**
	 * Test Insufficient Location Input, implement a partial address and expecting
	 * to get InsufficientLocationInput status
	 */
	public void testInsufficientLocationInput() {

		address.setCity("");
		assertFalse(addressValidatorService.validateAddressFields(address));


	}


	public void testGetAddressInformationFromGeocode() {
		JSONObject responseBody = null;
		try {
			responseBody = addressValidatorService.getAddressInformationFromGeocode(address);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNotNull(responseBody);
		
	}
}
