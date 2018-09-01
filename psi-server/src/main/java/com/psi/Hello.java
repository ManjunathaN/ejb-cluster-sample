package com.psi;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class Hello implements HelloRemote, HelloLocal {

	public String sayHello() {
		return "Hello From - Docker: "+System.getenv("dockerName");
	}
}
