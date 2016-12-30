package com.fortes.rh.security.spring.aop.callback.crud;

import com.fortes.model.AbstractModel;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.callback.AuditorCallbackImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.MetodoInterceptado;

public class InsertAuditorCallbackImpl extends AuditorCallbackImpl {

	//MUITO CUIDADO AO MEXER NAS ORDENS DOS METODOS ABAIXO.
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable {
		
		metodo.processa();
		setEntidade((AbstractModel) metodo.getParametros()[0]);
		inicializa(metodo);
		
		return new AuditavelImpl(getModulo(), getOperacao(), getChave(), getDados());
	}
}
