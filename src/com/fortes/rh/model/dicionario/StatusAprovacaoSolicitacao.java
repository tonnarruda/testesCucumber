package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class StatusAprovacaoSolicitacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 984000509902692769L;

	public static final char ANALISE = 'I';
	public static final char APROVADO = 'A';
	public static final char REPROVADO = 'R';

	public StatusAprovacaoSolicitacao()
	{
		put(ANALISE, "Em análise");
		put(APROVADO, "Aprovada");
		put(REPROVADO, "Reprovada");
	}
	
	public static String getDescricao(char tipo)
	{
		return new StatusAprovacaoSolicitacao().get(tipo);
	}
}
