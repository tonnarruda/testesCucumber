package com.fortes.rh.model.dicionario;

public enum TipoPessoa
{
	TODOS('T',"Todos"), 
	CANDIDATO('A', "Candidato"), 
	COLABORADOR('C',"Colaborador");

	TipoPessoa(char chave, String descricao)
	{
		this.chave = chave;
		this.descricao = descricao;
	}

	private char chave;
	private String descricao;

	public char getChave()
	{
		return chave;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

	public static String getDescricaoByChave(char chave)
	{
		for (TipoPessoa tipoPessoa : TipoPessoa.values())
		{
			if (tipoPessoa.getChave() == chave)
				return tipoPessoa.getDescricao();
		}
		return null;
	}
}
