package com.fortes.security.auditoria;


public interface AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable;
	
}
