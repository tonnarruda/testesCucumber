package com.fortes.rh.business.security;

public interface SecurityManager {

	/**
	 * Verifica se existe usuário logado na Aplicação.
	 */
	public boolean hasLoggedUser();
	
}
