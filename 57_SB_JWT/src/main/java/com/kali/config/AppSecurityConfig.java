package com.kali.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kali.filter.AppFilter;
import com.kali.service.CustomerService;

@Configuration
public class AppSecurityConfig {
	
	@Autowired
	private CustomerService customerService; //2
	
	@Autowired
	private AppFilter filter; //6
	
	@Bean
	public PasswordEncoder pwdEncoder() { //1
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider() { //3
		//here we are setting customer service and password encoder method
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(customerService);
		provider.setPasswordEncoder(pwdEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception { //4
		//authenticate manager to authenticate our login credentials
		return config.getAuthenticationManager();
	}

	public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception { //5
		return http.csrf()
					.disable()
					.authorizeRequests()
					.requestMatchers("/api/register", "/api/login")
					.permitAll()
					.and()
					.authorizeHttpRequests()
					.requestMatchers("/api/**")
					.authenticated()
					.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
					.authenticationProvider(authProvider())
					.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) //7
					.build();
	}
}
