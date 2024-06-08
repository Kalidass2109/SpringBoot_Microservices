package com.kali.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kali.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	public Customer findByUname(String uname);

}
