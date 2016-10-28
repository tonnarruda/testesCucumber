package com.fortes.rh.business.security;

import org.springframework.stereotype.Component;

import com.fortes.rh.security.SecurityUtil;

@Component
public class SecurityManagerImpl implements SecurityManager {

	public boolean hasLoggedUser() {
		return SecurityUtil.hasLoggedUser();
	}

}
