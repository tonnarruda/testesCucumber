package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public interface ColaboradorPeriodoExperienciaAvaliacaoManager extends GenericManager<ColaboradorPeriodoExperienciaAvaliacao>
{
	void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoesGestor);

	void removeConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador);

	Collection<ColaboradorPeriodoExperienciaAvaliacao> findByColaborador(Long colaboradorId);

	void enviaLembreteColaboradorAvaliacaoPeriodoExperienciaVencendo();

	void removeByAvaliacao(Long avaliacaoId);
}