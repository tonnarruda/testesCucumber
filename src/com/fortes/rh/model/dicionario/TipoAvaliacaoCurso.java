package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoAvaliacaoCurso extends LinkedHashMap 
{
	public static final char NOTA = 'n';
	public static final char PORCENTAGEM = 'p';
	
	@SuppressWarnings("unchecked")
	public TipoAvaliacaoCurso() 
	{
		put(NOTA, "Nota");
		put(PORCENTAGEM, "Porcentagem (%)");
	}
	
	public static String getDescricao(char tipo)
	{
		String retorno = "";
		switch (tipo)
		{
			case NOTA:
				retorno = "Nota";
				break;
			case PORCENTAGEM:
				retorno = "Porcentagem (%)";
				break;
		}

		return retorno;
	}
}
