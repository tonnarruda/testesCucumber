package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Reajuste extends LinkedHashMap
{
	private static final long serialVersionUID = 4151982962011616197L;

	private static final char COLABORADOR = 'C';
	private static final char CARGO = 'G';
	private static final char INDICE = 'I';

	public Reajuste()
	{
		put(COLABORADOR, "Colaborador");
		put(CARGO, "Cargo");
		put(INDICE, "Índice");
	}
	
	public String getReajusteDescricao(char reajuste)
	{
		return (String) new Reajuste().get((reajuste));
	}
}