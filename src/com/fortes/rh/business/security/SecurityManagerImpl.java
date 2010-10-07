package com.fortes.rh.business.security;

import com.fortes.rh.security.SecurityUtil;

public class SecurityManagerImpl implements SecurityManager {

	public boolean hasLoggedUser() {
		return SecurityUtil.hasLoggedUser();
	}

}
