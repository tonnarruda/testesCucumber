package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.DadosAuditados;
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
		
		String dados = new DadosAuditados(candidatoSolicitacaoAnteriorDados.toArray(), candidatoSolicitacaoAtualDados.toArray()).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), candidatoSolicitacaoAtual.getColaboradorNome(), dados);
	}
	
	public Auditavel moverCandidatos(MetodoInterceptado metodo) throws Throwable{
		Long[] candidatoSolicitacaoIds = (Long[]) metodo.getParametros()[0];
		Solicitacao solicitacaoOrigem = (Solicitacao) metodo.getParametros()[1];
		Solicitacao solicitacaoDestino = (Solicitacao) metodo.getParametros()[2];
		Boolean atualizarModelo = (Boolean) metodo.getParametros()[3];
		
		String candidatos = findNomeCandidatosTransferidos(metodo, candidatoSolicitacaoIds);
		
		Collection<Map<String, Object>> DadosAtual = new ArrayList<Map<String,Object>>();
		Map<String, Object> dadosMapAtual = new LinkedHashMap<String, Object>();
		dadosMapAtual.put("Data", DateUtil.formataDiaMesAno(new Date()));
		dadosMapAtual.put("Solicitação origem. Código", solicitacaoOrigem.getId());
		dadosMapAtual.put("Solicitação destino. Código", solicitacaoDestino.getId());
		dadosMapAtual.put("Transferiu respostas avaliação", atualizarModelo ? "Sim" : "Não");
		dadosMapAtual.put("Cadidatos transferidos:", candidatos);
		
		DadosAtual.add(dadosMapAtual);
		
		String dados = new DadosAuditados(null, DadosAtual.toArray()).gera();
		
		if(candidatos.length() > 254)
			candidatos = candidatos.substring(0, 254);
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), candidatos, dados);
	}

	private String findNomeCandidatosTransferidos(MetodoInterceptado metodo,Long[] candidatoSolicitacaoIds) throws Throwable {
		CandidatoSolicitacaoManager manager = (CandidatoSolicitacaoManager) metodo.getComponente();
		Collection<CandidatoSolicitacao> CandidatoSolicitacoes = manager.findById(candidatoSolicitacaoIds);
		metodo.processa();

		String candidatos = "";
		for (CandidatoSolicitacao candidatoSolicitacao : CandidatoSolicitacoes) 
			candidatos += candidatoSolicitacao.getCandidatoNome() + ", ";
		
		if(!candidatos.isEmpty())
			candidatos = candidatos.substring(0, candidatos.length() - 2);
		
		return candidatos;
	}

	private CandidatoSolicitacao carregaEntidade(MetodoInterceptado metodo, CandidatoSolicitacao candidatoSolicitacao) {
		CandidatoSolicitacaoManager manager = (CandidatoSolicitacaoManager) metodo.getComponente();
		return manager.findCandidatoSolicitacaoById(candidatoSolicitacao.getId());
	}
}
