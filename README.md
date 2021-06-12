# leadspace
Test for interview in leadspace

To run the application run the class com.leadspace.addressresolver.ResoleverApp as Java applicatio
By defaulet it will run on port 8080 but you can specified a port in application parameter or if running from consule in the CLI, just need to spcified a port number.
Rest API:
POST localhost:8083/address/resolv
Examole of bofy row:
{
	"country":"France",
	"city":"Paris",
	"state": "Île-de-France",
	"postalCode": "75002", 
	"street": "5 Rue Daunou"
	
}

Response: 
{
    "normalized": "5 Rue Daunou, 75002 Paris, France",
    "status": "Valid",
    "hereUsage": 1
}

For run the Tests run the com.leadspace.addressresolver.services.AddressValidatorServiceTest as JUnitest
