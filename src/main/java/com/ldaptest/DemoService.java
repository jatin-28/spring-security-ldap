package com.ldaptest;

import javax.annotation.security.RolesAllowed;

public class DemoService {

	@RolesAllowed("ROLE_USER")
	public void method()
	{
		System.out.println("Method called");
	}

}
