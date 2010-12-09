package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoModeloAvaliacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 1L;
	
	public static final char SOLICITACAO = 'S';
	public static final char DESEMPENHO = 'D';
	
	public TipoModeloAvaliacao()
	{
		put(SOLICITACAO, "Solicitação");
		put(DESEMPENHO, "Desempenho");
	}
}