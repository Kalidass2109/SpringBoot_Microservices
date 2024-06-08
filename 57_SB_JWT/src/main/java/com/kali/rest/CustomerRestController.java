package com.kali.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kali.entity.Customer;
import com.kali.repo.CustomerRepo;
import com.kali.service.JWTService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private JWTService jwt;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private CustomerRepo repo;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Programming";
	}
	
	@PostMapping("/register")
	public String registerCustomer(@RequestBody Customer customer) {
		String encodedPwd = pwdEncoder.encode(customer.getPwd());
		customer.setPwd(encodedPwd);
		
		repo.save(customer);
		
		return "Customer registered";
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Customer c){
		UsernamePasswordAuthenticationToken token=
				new UsernamePasswordAuthenticationToken(c.getUname(), c.getPwd());
		
		try {
			Authentication authenticate = authManager.authenticate(token);
			
			if(authenticate.isAuthenticated()) {
				String jwtToken = jwt.generateToken(c.getUname());
				return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	}
	
}
