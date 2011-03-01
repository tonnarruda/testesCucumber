package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.dicionario.Apto;

public class HistoricoCandidatoFactory
{

	public static HistoricoCandidato getEntity()
	{
		HistoricoCandidato entity = new HistoricoCandidato();
		entity.setId(1L);
		entity.setApto(Apto.INDIFERENTE);

		return entity;
	}

}
