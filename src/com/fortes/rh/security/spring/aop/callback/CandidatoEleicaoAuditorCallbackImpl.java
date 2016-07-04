package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;

import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class CandidatoEleicaoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel remove(MetodoInterceptado metodo) throws Throwable
	{
		Long candidatoEleicaoId = (Long) metodo.getParametros()[0];

		CandidatoEleicao candidatoEleicao = carregaCandidatoElelicao(metodo, candidatoEleicaoId);

		StringBuilder dados = new StringBuilder("");
		dados.append("Candidato da Eleição ID: " + candidatoEleicao.getId());
		dados.append("\nEleito: " + (candidatoEleicao.isEleito() ? "Sim" : "Não"));
		dados.append("\nColaborador: " + candidatoEleicao.getCandidato().getNome());
		dados.append("\nColaborador ID: " + candidatoEleicao.getCandidato().getId());

		metodo.processa();
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Remoção - " + candidatoEleicao.getCandidato().getNome(), dados.toString());
	}
	
	public Auditavel save(MetodoInterceptado metodo) throws Throwable
	{
		String[] candidatosCheck = (String[]) metodo.getParametros()[0];
		Eleicao eleicao = (Eleicao) metodo.getParametros()[1];
		CandidatoEleicaoManager manager = (CandidatoEleicaoManager) metodo.getComponente();
		Collection<CandidatoEleicao> candidatosAnteror = manager.findByEleicao(eleicao.getId());
		metodo.processa();

		StringBuilder dados = new StringBuilder("");
		for (String string : candidatosCheck) {
			CandidatoEleicao candidatoEleicao = manager.findByColaboradorIdAndEleicaoId(Long.parseLong(string), eleicao.getId());
			candidatoEleicao.setEleicao(eleicao);
			if (!candidatosAnteror.contains(candidatoEleicao)) {
				dados.append("Candidato da Eleição ID: " + candidatoEleicao.getId());
				dados.append("\nColaborador: " + candidatoEleicao.getCandidato().getNome());
				dados.append("\nColaborador ID: " + candidatoEleicao.getCandidato().getId());
				dados.append("\n\n");
			}
		}
		if (!dados.toString().isEmpty())
			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Inserção - Eleição ID: " + eleicao.getId(), dados.toString());
		else
			return null;
	}
	
	private CandidatoEleicao carregaCandidatoElelicao(MetodoInterceptado metodo, Long candidatoEleicaoId) 
	{
		CandidatoEleicaoManager manager = (CandidatoEleicaoManager) metodo.getComponente();
		return manager.findCandidatoEleicao(candidatoEleicaoId);
	}
}
