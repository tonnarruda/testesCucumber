package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class CrudAuditorCallbackImpl implements AuditorCallback {

	// XXX: talvez fosse melhor mudar essa abordagem!
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		AuditorCallback callback = getCallbackPeloNomeDoMetodo(metodo.getMetodo().getName());
		return callback.processa(metodo);
	}

	private AuditorCallback getCallbackPeloNomeDoMetodo(String nome) {
		if (nome.equals("save"))
			return new InsertAuditorCallbackImpl();
		else if (nome.equals("update"))
			return new UpdateAuditorCallbackImpl();
		else if (nome.equals("remove"))
			return new RemoveAuditorCallbackImpl();
		
		throw new IllegalArgumentException("Método interceptado não é um método de CRUD (save, update ou remove).");
	}

}
