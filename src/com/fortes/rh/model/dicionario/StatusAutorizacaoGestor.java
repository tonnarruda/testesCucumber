package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class StatusAutorizacaoGestor extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 984000509902692769L;

	public static final Character ANALISE = 'I';
	public static final Character AUTORIZADO = 'A';
	public static final Character REPROVADO = 'R';

	public StatusAutorizacaoGestor()
	{
		put(ANALISE, "Em análise");
		put(AUTORIZADO, "Autorizado");
		put(REPROVADO, "Não Autorizado");
	}
	
	public static String getDescricao(Character tipo)
	{
		if(tipo== null)
			return "";
		
		return new StatusAutorizacaoGestor().get(tipo);
	}
}
