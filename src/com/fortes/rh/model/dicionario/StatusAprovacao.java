package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class StatusAprovacao extends LinkedHashMap<Character, String>
{
	public static final char TODOS = 'T';
	public static final char APROVADO = 'A';
	public static final char REPROVADO = 'R';

	public StatusAprovacao()
	{
		put(TODOS, "Aprovados e Reprovados");
		put(APROVADO, "Aprovados");
		put(REPROVADO, "Reprovados");
	}
	
	public static String getDescricao(char tipo)
	{
		return new StatusAprovacaoSolicitacao().get(tipo);
	}
}
