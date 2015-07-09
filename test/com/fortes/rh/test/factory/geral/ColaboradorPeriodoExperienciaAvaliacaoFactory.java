package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorPeriodoExperienciaAvaliacao;

public class ColaboradorPeriodoExperienciaAvaliacaoFactory 
{
	public static ColaboradorPeriodoExperienciaAvaliacao getEntity()
	{
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = new ColaboradorPeriodoExperienciaAvaliacao();
		colaboradorPeriodoExperienciaAvaliacao.setId(null);

		return colaboradorPeriodoExperienciaAvaliacao;
	}

	public static ColaboradorPeriodoExperienciaAvaliacao getEntity(Long id)
	{
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = getEntity();
		colaboradorPeriodoExperienciaAvaliacao.setId(id);
		
		return colaboradorPeriodoExperienciaAvaliacao;
	}
	
	public static ColaboradorPeriodoExperienciaAvaliacao getEntity(Colaborador colaborador, PeriodoExperiencia periodoExperiencia, Avaliacao  avaliacao, char tipo)
	{
		ColaboradorPeriodoExperienciaAvaliacao colaboradorPeriodoExperienciaAvaliacao = getEntity();
		colaboradorPeriodoExperienciaAvaliacao.setColaborador(colaborador);
		colaboradorPeriodoExperienciaAvaliacao.setPeriodoExperiencia(periodoExperiencia);
		colaboradorPeriodoExperienciaAvaliacao.setAvaliacao(avaliacao);
		colaboradorPeriodoExperienciaAvaliacao.setTipo(tipo);

		return colaboradorPeriodoExperienciaAvaliacao;
	}
}
