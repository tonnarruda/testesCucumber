package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public interface ColaboradorPeriodoExperienciaAvaliacaoManager extends GenericManager<ColaboradorPeriodoExperienciaAvaliacao>
{
	void saveConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador, Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorAvaliacoes);

	void removeConfiguracaoAvaliacaoPeriodoExperiencia(Colaborador colaborador);
}