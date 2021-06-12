/**
 * 
 */
package com.leadspace.addressresolver.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
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
	 * Test method addressValidatorService.validateAddress That test will test the
	 * full validation
	 */
	public void testValidateAddress() {

		Resolve resolve = addressValidatorService.validateAddress(address);

		assertEquals("The reponse is not valid", resolve.getStatus(), DefaultParas.Valid);
		assertEquals("The Usage is not 1", resolve.getHereUsage(), 1);
		assertNotNull("The Normalized is null", resolve.getNormalized());

	}

	/**
	 * Test method addressValidatorService.validateAddress That test will test the
	 * full validation
	 */
	public void testValidateResponseItems() {

		try {
			JSONObject responseObject = addressValidatorService.getAddressInformationFromGeocode(address);
			assertNotNull(responseObject);
			assertEquals("The reponse is not valid",
					addressValidatorService.validateResponseItems(responseObject).getStatus(), DefaultParas.Valid);

			JSONObject error = new JSONObject("{\"error\": \"UnitesError\"}");
			
			assertEquals("Got Error and the status is not Unknown",
					addressValidatorService.validateResponseItems(error).getStatus(), DefaultParas.Unknown);
			
			JSONArray items = responseObject.getJSONArray("items");
			JSONObject itemObj = items.getJSONObject(0);
			JSONObject addressObj = itemObj.getJSONObject("address");
			
			String responseAsString = responseObject.toString();
			
			//Test if there is no "houseNumber"
			JSONObject fixedResponse = new JSONObject(responseAsString.replaceAll("5", ""));
			
			assertEquals("The reponse is not valid",
					addressValidatorService.validateResponseItems(fixedResponse).getStatus(), DefaultParas.Invalid);
			
			
			//Here.com API returns empty label field

			fixedResponse = new JSONObject(responseAsString.replaceAll(addressObj.getString("label"), ""));
			
			assertEquals("The reponse is not valid",
					addressValidatorService.validateResponseItems(fixedResponse).getStatus(), DefaultParas.Invalid);
			
			//The street field contain “Mailbox”
			fixedResponse = new JSONObject(responseAsString.replaceAll(addressObj.getString("street"), "Mailbox"));
			
			assertEquals("The reponse is not valid",
					addressValidatorService.validateResponseItems(fixedResponse).getStatus(), DefaultParas.Invalid);


		} catch (Exception e) {
			fail(e.getMessage());
		}

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
			fail(e.getMessage());
		}
		assertNotNull(responseBody);

	}
}
