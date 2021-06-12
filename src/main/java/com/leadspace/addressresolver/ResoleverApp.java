package com.leadspace.addressresolver;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResoleverApp {
	
	

	public static void main(String[] args) {
		//Running the server with the specified port 
		
		String port = args[0] == null ? DefaultParas.PORT : args[0]; 
		
		SpringApplication app = new SpringApplication(ResoleverApp.class);
		
		Map<String, Object> prop = new HashMap<String, Object>();
		prop.put("server.port", port);
		app.setDefaultProperties(prop);
        app.run(args);
	  }
}
