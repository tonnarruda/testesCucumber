package com.fortes.rh.model.dicionario;


public enum ResultadoExame
{
	NORMAL("Normal"),ANORMAL("Alterado"),NAO_REALIZADO("Não Informado");

	ResultadoExame(String descricao)
	{
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao()
	{
		return descricao;
	}
}
