package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.rh.security.spring.aop.GeraDadosAuditados;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class SolicitacaoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	public Auditavel updateStatusSolicitacao(MetodoInterceptado metodo) throws Throwable {
		

		Solicitacao solicitacao = (Solicitacao) metodo.getParametros()[0];
		Solicitacao solicitacaoAnterior = (Solicitacao) carregaEntidade(metodo, solicitacao);
		metodo.processa();
		
		Map<String, String> dadosAnteriores = new HashMap<String, String>();
		dadosAnteriores.put("status", solicitacaoAnterior.getStatusFormatado());

		if(solicitacaoAnterior.getLiberador().getId() != null)
			dadosAnteriores.put("liberadorId", solicitacaoAnterior.getLiberador().getId().toString());
		else
			dadosAnteriores.put("liberadorId", "");

		dadosAnteriores.put("liberadorNome", solicitacaoAnterior.getLiberador().getNome());
		dadosAnteriores.put("liberadorObservacao", solicitacaoAnterior.getObservacaoLiberador());

		Map<String, String> dadosAtualizados = new HashMap<String, String>();
		dadosAtualizados.put("status", String.valueOf(solicitacao.getStatusFormatado()));
		dadosAtualizados.put("liberadorId", solicitacao.getLiberador().getId().toString());
		dadosAtualizados.put("liberadorNome", solicitacao.getLiberador().getNome());
		dadosAtualizados.put("liberadorObservacao", solicitacao.getObservacaoLiberador());
		
		String dados = new GeraDadosAuditados(new Object[]{dadosAnteriores}, dadosAtualizados).gera();
		
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), solicitacaoAnterior.getDescricaoFormatada(), dados);
	}
	
	private Solicitacao carregaEntidade(MetodoInterceptado metodo, Solicitacao solicitacao) {
		SolicitacaoManager manager = (SolicitacaoManager) metodo.getComponente();
		return manager.findByIdProjectionForUpdate(solicitacao.getId());
	}
}
