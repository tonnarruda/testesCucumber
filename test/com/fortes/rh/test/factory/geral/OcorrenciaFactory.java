package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Ocorrencia;

public class OcorrenciaFactory
{
	public static Ocorrencia getEntity()
	{
		Ocorrencia ocorrencia = new Ocorrencia();

		ocorrencia.setDescricao("descricao");
		ocorrencia.setPontuacao(1);

		return ocorrencia;
	}

	public static Ocorrencia getEntity(Long id)
	{
		Ocorrencia ocorrencia = getEntity();
		ocorrencia.setId(id);
		return ocorrencia;
	}
}
