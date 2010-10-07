package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.MotivoSolicitacao;

public class MotivoSolicitacaoFactory
{
	public static MotivoSolicitacao getEntity()
	{
		MotivoSolicitacao motivoSolicitacao = new MotivoSolicitacao();
		motivoSolicitacao.setId(null);
		motivoSolicitacao.setDescricao("descricao");

		return motivoSolicitacao;
	}
}
