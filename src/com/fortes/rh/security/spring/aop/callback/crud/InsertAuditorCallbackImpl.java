package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.callback.AuditorCallbackImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.MetodoInterceptado;

public class InsertAuditorCallbackImpl extends AuditorCallbackImpl {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		metodo.processa();
		setEntidade(carregaEntidade(metodo));
		inicializa(metodo);
		
		return new AuditavelImpl(getModulo(), getOperacao(), getChave(), getDados());
	}

}
