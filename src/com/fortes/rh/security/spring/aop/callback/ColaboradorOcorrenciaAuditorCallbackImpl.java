package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ColaboradorOcorrenciaAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}
	
	public Auditavel saveColaboradorOcorrencia(MetodoInterceptado metodo) throws Throwable {
		
		ColaboradorOcorrencia colaboradorOcorrencia = (ColaboradorOcorrencia) metodo.getParametros()[0];
		ColaboradorOcorrencia colaboradorOcorrenciaAnterior = (ColaboradorOcorrencia) carregaEntidade(metodo, colaboradorOcorrencia);
		
		metodo.processa();
		
		colaboradorOcorrencia = carregaEntidade(metodo, colaboradorOcorrencia);
		
		String dados = new GeraDadosAuditados(new Object[]{colaboradorOcorrenciaAnterior}, colaboradorOcorrencia).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaboradorOcorrencia.getOcorrencia().getDescricao(), dados);
	}

	public Auditavel remove(MetodoInterceptado metodo) throws Throwable {
		
		ColaboradorOcorrencia colaboradorOcorrencia = (ColaboradorOcorrencia) metodo.getParametros()[0];
		ColaboradorOcorrencia colaboradorOcorrenciaAnterior = (ColaboradorOcorrencia) carregaEntidade(metodo, colaboradorOcorrencia);
		
		metodo.processa();
		
		String dados = new GeraDadosAuditados(new Object[]{colaboradorOcorrenciaAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), colaboradorOcorrenciaAnterior.getOcorrencia().getDescricao(), dados);
	}
	
	private ColaboradorOcorrencia carregaEntidade(MetodoInterceptado metodo, ColaboradorOcorrencia colaboradorOcorrencia) {
		ColaboradorOcorrenciaManager manager = (ColaboradorOcorrenciaManager) metodo.getComponente();
		return manager.findByIdProjection(colaboradorOcorrencia.getId());
	}
}
