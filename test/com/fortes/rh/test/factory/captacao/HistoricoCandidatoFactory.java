package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.HistoricoCandidato;

public class HistoricoCandidatoFactory
{

	public static HistoricoCandidato getEntity()
	{
		HistoricoCandidato entity = new HistoricoCandidato();
		entity.setId(1L);

		return entity;
	}

}
