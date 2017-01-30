package com.howtodoinjava;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-security.xml"})
public class TestDemoService {

	@Autowired
	private DemoService testObj;

	@Autowired
	private InMemoryUserDetailsManager userDetailsService;

	/**
	 * Test the valid user with valid role
	 * */
	@Test 
	public void testValidRole()
	{
		//Get the user by username from configured user details testObj
		UserDetails userDetails = userDetailsService.loadUserByUsername ("lokesh");
		Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		testObj.method();
	}
	
	/**
	 * Test the valid user with INVALID role
	 * */
	@Test (expected = AccessDeniedException.class)
	public void testInvalidRole()
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername ("lokesh");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);

		testObj.method();
	}
	
	/**
	 * Test the INVALID user 
	 * */
	@Test (expected = AccessDeniedException.class)
	public void testInvalidUser()
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername ("admin");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
		Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
		SecurityContextHolder.getContext().setAuthentication(authToken);

		testObj.method();
	}
	
}
