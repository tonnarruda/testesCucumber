package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.ColaboradorOcorrencia;

public class ColaboradorOcorrenciaFactory
{
	public static ColaboradorOcorrencia getEntity()
	{
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();

		return colaboradorOcorrencia;
	}

	public static ColaboradorOcorrencia getEntity(Long id)
	{
		ColaboradorOcorrencia colaboradorOcorrencia = getEntity();
		colaboradorOcorrencia.setId(id);

		return colaboradorOcorrencia;
	}
}
