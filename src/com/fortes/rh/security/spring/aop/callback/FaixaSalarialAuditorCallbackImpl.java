package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class FaixaSalarialAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}
	
	public Auditavel saveFaixaSalarial(MetodoInterceptado metodo) throws Throwable 
	{
		FaixaSalarial faixa = (FaixaSalarial) metodo.getParametros()[0];
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(null, faixa).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), faixa.getDescricao(), dados);
	}
}
