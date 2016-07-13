package com.fortes.rh.security.spring.aop.callback;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.avaliacao.ParticipanteAvaliacaoDesempenho;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.security.spring.aop.AuditavelImpl;
import com.fortes.security.auditoria.Auditavel;
import com.fortes.security.auditoria.AuditorCallback;
import com.fortes.security.auditoria.MetodoInterceptado;

public class ParticipanteAvaliacaoDesempenhoAuditorCallbackImpl implements AuditorCallback {

	public Auditavel processa(MetodoInterceptado metodo) throws Throwable 
	{
		Method method = this.getClass().getMethod(metodo.getMetodo().getName(), MetodoInterceptado.class);
		return (Auditavel) method.invoke(this, metodo);
	}

	
	@SuppressWarnings("unchecked")
	public Auditavel save(MetodoInterceptado metodo) throws Throwable 
	{
		try {
			AvaliacaoDesempenho avaliacaoDesempenho = (AvaliacaoDesempenho) metodo.getParametros()[0];
			Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliados = (Collection<ParticipanteAvaliacaoDesempenho>) metodo.getParametros()[1];
			Collection<ParticipanteAvaliacaoDesempenho> participantesAvaliadores = (Collection<ParticipanteAvaliacaoDesempenho>) metodo.getParametros()[2];
			Collection<ColaboradorQuestionario> colaboradorQuestionarios = (Collection<ColaboradorQuestionario>) metodo.getParametros()[3];
			participantesAvaliados.removeAll(Collections.singleton(null));
			participantesAvaliadores.removeAll(Collections.singleton(null));
			colaboradorQuestionarios.removeAll(Collections.singleton(null));

			metodo.processa();

			StringBuilder dados = new StringBuilder();
			dados.append("\nAvaliação de Desempenho: " + avaliacaoDesempenho.getTitulo());
			dados.append("\n\nAvaliados: ");

			for (ParticipanteAvaliacaoDesempenho participantesAvaliado : participantesAvaliados) {
				dados.append("\nId: " + participantesAvaliado.getColaborador().getId());
				dados.append("\nNome: " + participantesAvaliado.getColaborador().getNome());
				dados.append("\nProdutividade: " + participantesAvaliado.getProdutividadeString());
				dados.append("\n");
			}

			dados.append("\n\nAvaliadores: ");

			for (ParticipanteAvaliacaoDesempenho participantesAvaliador : participantesAvaliadores) {
				dados.append("\nId: " + participantesAvaliador.getColaborador().getId());
				dados.append("\nNome: " + participantesAvaliador.getColaborador().getNome());
				dados.append("\n");
			}

			Long avaliadorId = null; 
			for (ColaboradorQuestionario colaboradorQuestionario : colaboradorQuestionarios) {
				if(colaboradorQuestionario.getAvaliador() != null && !colaboradorQuestionario.getAvaliador().getId().equals(avaliadorId)){
					dados.append("\n\nAvaliador " + colaboradorQuestionario.getAvaliador().getNome() + " avaliará os seguintes avaliados:");
					dados.append("\nPeso: " + colaboradorQuestionario.getPesoAvaliador());
				}
				dados.append("\n - " + colaboradorQuestionario.getColaborador().getNome());
				if(colaboradorQuestionario.getColaborador() != null && colaboradorQuestionario.getColaborador().getId().equals(colaboradorQuestionario.getAvaliador().getId()))
					dados.append(" - Peso na autoavaliação: " + colaboradorQuestionario.getPesoAvaliador());
				avaliadorId = colaboradorQuestionario.getAvaliador().getId();
			}

			return new AuditavelImpl(metodo.getModulo(), metodo.getOperacao(), "Configuração dos participantes da avaliação", dados.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}