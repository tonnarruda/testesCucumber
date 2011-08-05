package com.fortes.rh.model.dicionario;


public class TipoCompetencia
{
	public static final Character CONHECIMENTO = 'C';
	public static final Character HABILIDADE = 'H';
	public static final Character ATITUDE = 'A';
	
	public static Character getConhecimento()
	{
		return CONHECIMENTO;
	}

	public static Character getHabilidade()
	{
		return HABILIDADE;
	}

	public static Character getAtitude()
	{
		return ATITUDE;
	}
}