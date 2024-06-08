package com.kali.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kali.clients.WelcomeApiFeignClient;

@RestController
public class GreetRestController {

	@Autowired
	private WelcomeApiFeignClient welcomeClient;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/greet")
	public String greetmsg() {
		
		String welcomeRsep = welcomeClient.invokeWelcomeApi();
		
		String port= env.getProperty("server.port");
		
		String greetRsep="Good Morning, ("+port+"), ";
		
		return greetRsep+welcomeRsep;
	}

}
