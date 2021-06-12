# leadspace
Test for interview in leadspace<br>

To run the application run the class com.leadspace.addressresolver.ResoleverApp as Java applicatio<br>
By defaulet it will run on port 8080 but you can specified a port in application parameter or if running from consule in the CLI, just need to spcified a port number.<br>
Rest API:<br>
POST localhost:8083/address/resolv<br>
Examole of bofy row:<br>
{<br>
	&#9"country":"France",<br>
	&#9"city":"Paris",<br>
	&#9"state": "Île-de-France",<br>
	&#9"postalCode": "75002", <br>
	&#9"street": "5 Rue Daunou"<br>
	
}<br>
<br>
Response: <br>
{<br>
    "normalized": "5 Rue Daunou, 75002 Paris, France",<br>
    "status": "Valid",<br>
    "hereUsage": 1<br>
}<br>

For run the Tests run the com.leadspace.addressresolver.services.AddressValidatorServiceTest as JUnitest
