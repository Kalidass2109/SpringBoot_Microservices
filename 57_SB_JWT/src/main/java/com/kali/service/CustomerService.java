package com.kali.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kali.entity.Customer;
import com.kali.repo.CustomerRepo;

@Service
public class CustomerService implements UserDetailsService{
	
	@Autowired
	private CustomerRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer customer = repo.findByUname(username);
		
		return new User(customer.getUname(), customer.getPwd(), Collections.emptyList());
	}

}
