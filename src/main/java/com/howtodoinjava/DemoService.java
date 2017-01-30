package com.howtodoinjava;

import org.springframework.security.access.annotation.Secured;

public class DemoService {

	@Secured("ROLE_USER")
	public void method()
	{
		System.out.println("Method called");
	}

}
