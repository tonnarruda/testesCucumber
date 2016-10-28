package com.fortes.rh.security.spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManager;
import com.fortes.security.auditoria.Audita;

@Component
@SuppressWarnings("serial")
public class AuditoriaPointcut extends StaticMethodMatcherPointcutAdvisor {

	@SuppressWarnings("unchecked")
	public boolean matches(Method method, Class targetClass) {
		boolean audita = this.deveSerAuditado(method, targetClass);
		return audita;
	}
	
	/**
	 * Verifica se o método deve ser auditado.
	 */
	@SuppressWarnings("unchecked")
	private boolean deveSerAuditado(Method method, Class targetClass) {
		// verifica se eh um Manager
		boolean ehDoTipoManager = this.ehDoTipoManager(targetClass);
		if (!ehDoTipoManager)
			return false;
		// verifica se possui a anotação @Audita
		boolean temAnotacaoDeAuditoria = temAnotacaoDeAuditoria(method, targetClass);
		return temAnotacaoDeAuditoria;
	}
	/**
	 * Verifica se o método interceptado possui a anotação @Audita.
	 */
	@SuppressWarnings("unchecked")
	private boolean temAnotacaoDeAuditoria(Method method, Class targetClass) {
		// verifica se possui a anotação @Audita
		boolean temAnotacaoDeAuditoria = method.isAnnotationPresent(Audita.class);
		if (temAnotacaoDeAuditoria)
			return true;
		// The method may be on an interface, so let's check on the target class as well.
		Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
		temAnotacaoDeAuditoria = (specificMethod != method 
									&& specificMethod.isAnnotationPresent(Audita.class));
		return temAnotacaoDeAuditoria;
	}
	/**
	 * Verifica se a target class é do tipo Manager.
	 */
	@SuppressWarnings("unchecked")
	private boolean ehDoTipoManager(Class targetClass) {
		return GenericManager.class.isAssignableFrom(targetClass);
	}

}
