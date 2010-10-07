package com.fortes.rh.security.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.fortes.rh.business.security.AuditoriaManager;
import com.fortes.rh.business.security.SecurityManager;
import com.fortes.rh.security.spring.aop.callback.MetodoInterceptadoImpl;
import com.fortes.rh.security.spring.aop.callback.crud.CrudAuditorCallbackImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.EmptyAuditorCallbackImpl;
import com.fortes.security.auditoria.MetodoInterceptado;

public class AuditoriaGeralAdvice implements MethodInterceptor {

	private static final Logger logger = Logger.getLogger(AuditoriaGeralAdvice.class);
	
	private AuditoriaManager auditoriaManager;
	private SecurityManager securityManager;
	
	public void setAuditoriaManager(AuditoriaManager auditoriaManager) {
		this.auditoriaManager = auditoriaManager;
	}
	
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		logger.info("Auditando o método: " + invocation.getMethod());
		
		boolean hasLoggedUser = securityManager.hasLoggedUser();
		if(!hasLoggedUser)
			return invocation.proceed(); // executa sem auditoria
		
		MetodoInterceptado metodo = new MetodoInterceptadoImpl(invocation);
		
		Object resultado = this.audita(metodo);
		return resultado;
	}

	private Object audita(MetodoInterceptado metodo) throws Throwable {
		
		AuditorCallback callback = this.getAuditorCallback(metodo);
		Auditavel auditavel = callback.processa(metodo);
		
		String chave = auditavel.getChave();
		String dados = auditavel.getDados();
		String modulo = auditavel.getModulo();
		String operacao = auditavel.getOperacao();
		
		auditoriaManager.audita(modulo, operacao, chave, dados);
		
		return metodo.getRetornoDoMetodo();
	}
	/**
	 * Retorna a implementação de Callback do Auditor. Caso nenhum Callback
	 * tenha sido informado então a implementação default,
	 * CrudAuditorCallbackImpl, será utilizada.
	 */
	private AuditorCallback getAuditorCallback(MetodoInterceptado metodo) {
		AuditorCallback callback = metodo.getAuditorCallback();
		if (callback instanceof EmptyAuditorCallbackImpl)
			callback = new CrudAuditorCallbackImpl();
		return callback;
	}
	
}
