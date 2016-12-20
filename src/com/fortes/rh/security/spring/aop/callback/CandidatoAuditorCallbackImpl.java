package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;

import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class CandidatoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel update(MetodoInterceptado metodo) throws Throwable {
		
		Candidato candidato = (Candidato) metodo.getParametros()[0];
		Candidato candidatoAnterior = (Candidato) carregaEntidade(metodo, candidato);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{candidatoAnterior}, candidato).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), candidato.getNome(), dados);
	}
	
	public Auditavel removeCandidato(MetodoInterceptado metodo) throws Throwable {
		
		Candidato candidato = (Candidato) metodo.getParametros()[0];
		Candidato candidatoAnterior = (Candidato) carregaEntidade(metodo, candidato);
		
		metodo.processa();
		
		String dados = new DadosAuditados(new Object[]{candidatoAnterior}, null).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), candidatoAnterior.getNome(), dados);
	}
	
	private Candidato carregaEntidade(MetodoInterceptado metodo, Candidato candidato) {
		CandidatoManager manager = (CandidatoManager) metodo.getComponente();
		return manager.findEntidadeComAtributosSimplesById(candidato.getId());
	}
}
