package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;


public class CandidatoSolicitacaoAuditorCallbackImpl implements AuditorCallback {
	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel updateStatusAutorizacaoGestor(MetodoInterceptado metodo) throws Throwable 
	{
		CandidatoSolicitacao candidatoSolicitacaoAtual = (CandidatoSolicitacao) metodo.getParametros()[0];
		CandidatoSolicitacao candidatoSolicitacaoAnterior = carregaEntidade(metodo, candidatoSolicitacaoAtual);
		
		metodo.processa();
		
		Collection<Map<String, Object>> candidatoSolicitacaoAtualDados = new ArrayList<Map<String,Object>>();
		Collection<Map<String, Object>> candidatoSolicitacaoAnteriorDados = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> candidatoSolicitacaoMapAtual = new LinkedHashMap<String, Object>();
		candidatoSolicitacaoMapAtual.put("Data", DateUtil.formataDiaMesAno(new Date()));
		candidatoSolicitacaoMapAtual.put("Status Aprovação", candidatoSolicitacaoAtual.getStatusAutorizacaoGestorFormatado());
		candidatoSolicitacaoMapAtual.put("Obs do Gestor", candidatoSolicitacaoAtual.getObsAutorizacaoGestor());
		candidatoSolicitacaoAtualDados.add(candidatoSolicitacaoMapAtual);
		
		Map<String, Object> candidatoSolicitacaoMapAnterior = new LinkedHashMap<String, Object>();
		candidatoSolicitacaoMapAnterior.put("Data", candidatoSolicitacaoAnterior.getDataAutorizacaoGestor() == null ? "Sem data" : DateUtil.formataDiaMesAno(candidatoSolicitacaoAnterior.getDataAutorizacaoGestor()));
		candidatoSolicitacaoMapAnterior.put("Status Aprovação", candidatoSolicitacaoAnterior.getStatusAutorizacaoGestorFormatado());
		candidatoSolicitacaoMapAnterior.put("Obs do Gestor", candidatoSolicitacaoAnterior.getObsAutorizacaoGestor() == null ? "Sem Obs" : candidatoSolicitacaoAnterior.getObsAutorizacaoGestor());
		candidatoSolicitacaoAnteriorDados.add(candidatoSolicitacaoMapAnterior);
		
		String dados = new GeraDadosAuditados(candidatoSolicitacaoAnteriorDados.toArray(), candidatoSolicitacaoAtualDados.toArray()).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), candidatoSolicitacaoAtual.getColaboradorNome(), dados);
	}
	
	private CandidatoSolicitacao carregaEntidade(MetodoInterceptado metodo, CandidatoSolicitacao candidatoSolicitacao) {
		CandidatoSolicitacaoManager manager = (CandidatoSolicitacaoManager) metodo.getComponente();
		return manager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
	}
}
