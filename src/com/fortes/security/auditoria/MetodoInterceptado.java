package com.fortes.security.auditoria;

import java.lang.reflect.Method;

public interface MetodoInterceptado {

	public Object processa() throws Throwable;
	
	public Method getMetodo();
	
	public Object[] getParametros();
	
	public Object getComponente();
	
	public Object getRetornoDoMetodo();
	
	public AuditorCallback getAuditorCallback();
	
	public String getModulo();
	
	public String getOperacao();
	
}
