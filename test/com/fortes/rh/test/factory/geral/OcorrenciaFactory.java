package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Empresa;
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
	
	public static Ocorrencia getEntity(Empresa empresa, String descricao, int pontuacao)
	{
		Ocorrencia ocorrencia = getEntity();
		ocorrencia.setEmpresa(empresa);
		ocorrencia.setDescricao(descricao);
		ocorrencia.setPontuacao(pontuacao);
		return ocorrencia;
	}
	
}
