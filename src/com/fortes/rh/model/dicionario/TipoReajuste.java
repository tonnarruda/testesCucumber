package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoReajuste extends LinkedHashMap<Character, String>
{
	public static final char COLABORADOR = 'C';
	public static final char FAIXA_SALARIAL = 'F';
	public static final char INDICE = 'I';

	public TipoReajuste()
	{
		put(COLABORADOR, "Colaborador");
		put(FAIXA_SALARIAL, "Faixa Salarial");
		put(INDICE, "Índice");
	}
	
	public static String getReajusteDescricao(Character tipoReajuste)
	{
		if (tipoReajuste == null)
			return "";
		
		return (String) new TipoReajuste().get((tipoReajuste));
	}
}