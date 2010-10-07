package com.fortes.rh.model.dicionario;

public enum SituacaoSolicitacaoEpi
{
	TODAS("Todas"), ABERTA("Aberta"), ENTREGUE("Entregue");

	SituacaoSolicitacaoEpi(String descricao)
	{
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao()
	{
		return descricao;
	}

}
