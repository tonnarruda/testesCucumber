package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class OcorrenciaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}
	
	public Auditavel saveOrUpdate(MetodoInterceptado metodo) throws Throwable {
		
		Ocorrencia ocorrencia = (Ocorrencia) metodo.getParametros()[0];
		
		Ocorrencia ocorrenciaAnterior = new Ocorrencia();
		if(ocorrencia.getId() != null)
			ocorrenciaAnterior = (Ocorrencia) (carregaEntidade(metodo, ocorrencia)).clone();
		
		
		metodo.processa();
		
		String dados = new DadosAuditados(ocorrenciaAnterior.getId() != null ? new Object[]{ocorrenciaAnterior} : null, ocorrencia).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), ocorrenciaAnterior.getId() != null ? ocorrenciaAnterior.getDescricao() : ocorrencia.getDescricao(), dados);
	}

	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		Ocorrencia ocorrencia = (Ocorrencia) metodo.getParametros()[0];
		Ocorrencia ocorrenciaAnterior = (Ocorrencia) carregaEntidade(metodo, ocorrencia);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{ocorrenciaAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), ocorrenciaAnterior.getDescricao(), dados);
	}
	
	private Ocorrencia carregaEntidade(MetodoInterceptado metodo, Ocorrencia ocorrencia) {
		if (ocorrencia != null && ocorrencia.getId() != null && ocorrencia.getId() > 0) {
			OcorrenciaManager manager = (OcorrenciaManager) metodo.getComponente();
			return manager.findById(ocorrencia.getId());
		}
		return null;
	}
}
