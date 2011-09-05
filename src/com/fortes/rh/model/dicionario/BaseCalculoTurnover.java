package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class BaseCalculoTurnover extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 1L;
	
	public static final char CONTRATADOS = 'C';
	public static final char SOLICITACAO = 'S';
	
	public BaseCalculoTurnover()
	{
		put(CONTRATADOS, "Todos os colaboradores contratados no período");
		put(SOLICITACAO, "Apenas os colaboradores contratados através de uma solicitação cujo motivo está marcado como turnover");
	}

	public char getSolicitacao() {
		return SOLICITACAO;
	}

	public char getContratados() {
		return CONTRATADOS;
	}
}