package com.psi;

import javax.ejb.Remote;

@Remote
public interface HelloRemote {
	String sayHello();
}
