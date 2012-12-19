package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TipoReajuste extends LinkedHashMap
{
	private static final long serialVersionUID = 4151982962011616197L;

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