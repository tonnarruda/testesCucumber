package com.fortes.security.auditoria;


public class EmptyAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		throw new IllegalStateException("Impossível executar Auditor Callback quando o mesmo não foi indicado.");
	}

}
