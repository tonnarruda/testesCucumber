package com.fortes.rh.security.spring.aop;

import java.lang.reflect.Method;

import net.vidageek.mirror.dsl.Mirror;

import org.aopalliance.intercept.MethodInvocation;

import com.fortes.security.auditoria.Audita;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;
import com.fortes.security.auditoria.Modulo;

public class MetodoInterceptadoImpl implements MetodoInterceptado {

	private final MethodInvocation invocation;
	
	private Object result;
	private boolean invoked = false;
	
	private Audita auditaAnnotation;

	public MetodoInterceptadoImpl(MethodInvocation invocation) {
		this.invocation = invocation;
		this.auditaAnnotation = new Mirror().on(getMetodo())
					.reflect().annotation(Audita.class);
	}

	public Object getComponente() {
		return invocation.getThis();
	}

	public Method getMetodo() {
		return invocation.getMethod();
	}

	public Object[] getParametros() {
		return invocation.getArguments();
	}

	public Object getRetornoDoMetodo() {
		if (!invoked)
			throw new IllegalStateException("Método Interceptado ainda não invocado.");
		return result;
	}

	public Object processa() throws Throwable {
		invoked = true;
		result = invocation.proceed();
		return result;
	}

	public AuditorCallback getAuditorCallback() {
		try {
			return auditaAnnotation.auditor().newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public String getModulo() {
		// FIXME: deveria pesquisar por todas as interfaces
		Class<?> principalInterface = getComponente().getClass().getInterfaces()[0]; // melhorar
		Modulo modulo = new Mirror().on(principalInterface)
							.reflect().annotation(Modulo.class)
							.atClass();
		if (modulo == null)
			return principalInterface.getSimpleName().replaceAll("Manager", "");
		return modulo.value();
	}

	public String getOperacao() {
		return auditaAnnotation.operacao();
	}

}
