package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class GerenciadorComunicacaoAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel enviaMensagemCancelamentoContratacao(MetodoInterceptado metodo) throws Throwable {
		
		Colaborador colaborador = (Colaborador) metodo.getParametros()[0];
		String mensagem = (String) metodo.getParametros()[1];
		
		metodo.processa();
		
		String dados = mensagem;
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaborador.getNome(), dados);
	}
}
