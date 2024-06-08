package com.kali.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankRestController {
	
	@GetMapping("/about")
	public String about() {
		return "We are into banking since 2000";
	}
	
	@GetMapping("/contact")
	public String contactUs() {
		return "Plz call :: +91 9877563498";
	}
	
	@GetMapping("/deposit")
	public String deposit() {
		return "Deposit success...";
	}
	
	@GetMapping("/withdraw")
	public String withdraw() {
		return "Withdraw success...";
	}

}
