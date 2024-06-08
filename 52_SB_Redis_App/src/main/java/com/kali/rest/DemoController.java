package com.kali.rest;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	private HashOperations<String, Object, Object> opsForHash;
	
	public DemoController(RedisTemplate<String, String> rt) {
		this.opsForHash= rt.opsForHash();
	}
	
	@PostMapping("/user")
	public String saveData() {
		opsForHash.put("USERS", 102, "Kalidass");
		return "data saved";
	}
	
	@GetMapping("/user")
	public String getUserName() {
		return (String) opsForHash.get("USERS", 102);
	}
	
	@GetMapping("/users")
	public Map<Object, Object> getAllUsers(){
		return opsForHash.entries("USERS");
	}

}
