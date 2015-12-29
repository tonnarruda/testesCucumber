package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;

import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ConfiguracaoCompetenciaAvaliacaoDesempenhoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	
	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos = (Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho>) metodo.getParametros()[0]; 
		AvaliacaoDesempenho avaliacaoDesempenho = (AvaliacaoDesempenho) metodo.getParametros()[1];
		metodo.processa();

		StringBuilder dados = new StringBuilder();
		dados.append("\nAvaliação de Desempenho: " + avaliacaoDesempenho.getTitulo());
		
		Long avaliadorId = null;
		Long faixaSalarialId = null; 
		for (ConfiguracaoCompetenciaAvaliacaoDesempenho configuracaoCompetenciaAvaliacaoDesempenho : configuracaoCompetenciaAvaliacaoDesempenhos) {
			if(!configuracaoCompetenciaAvaliacaoDesempenho.getAvaliador().getId().equals(avaliadorId)){
				dados.append("\n\nAvaliador " + configuracaoCompetenciaAvaliacaoDesempenho.getAvaliador().getNome() + " avaliará as seguintes competências:");
			}
			if(!configuracaoCompetenciaAvaliacaoDesempenho.getConfiguracaoNivelCompetenciaFaixaSalarial().getFaixaSalarial().getId().equals(faixaSalarialId)){
				dados.append("\n	- Faixa Salarial: " + configuracaoCompetenciaAvaliacaoDesempenho.getConfiguracaoNivelCompetenciaFaixaSalarial().getFaixaSalarial().getAtributoDescricao());
			}
			dados.append("\n		- " + configuracaoCompetenciaAvaliacaoDesempenho.getCompetenciaDescricao());
			avaliadorId = configuracaoCompetenciaAvaliacaoDesempenho.getAvaliador().getId();
			faixaSalarialId = configuracaoCompetenciaAvaliacaoDesempenho.getConfiguracaoNivelCompetenciaFaixaSalarial().getFaixaSalarial().getId();
		}
		return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Configuração das competências da avaliação", dados.toString());
	}
}