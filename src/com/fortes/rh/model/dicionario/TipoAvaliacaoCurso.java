package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoAvaliacaoCurso extends LinkedHashMap<Character, String> 
{
	public static final char NOTA = 'n';
	public static final char PORCENTAGEM = 'p';
	public static final char AVALIACAO = 'a';
	
	public TipoAvaliacaoCurso() 
	{
		put(NOTA, "Nota");
		put(PORCENTAGEM, "Porcentagem (%)");
		put(AVALIACAO, "Avaliação");
	}
	
	public static String getDescricao(char tipo)
	{
		TipoAvaliacaoCurso tipoAvaliacaoCurso = new TipoAvaliacaoCurso();
		
		if (!tipoAvaliacaoCurso.containsKey(tipo))
			return "";
		
		return tipoAvaliacaoCurso.get(tipo);
	}
}
