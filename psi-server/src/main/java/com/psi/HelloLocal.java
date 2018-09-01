package com.psi;

import javax.ejb.Local;

@Local
public interface HelloLocal {
	String sayHello();
}
