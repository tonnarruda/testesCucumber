package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoColaborador extends LinkedHashMap
{
	public static final String TODOS = "T";
	public static final String DESLIGADO = "D";
	public static final String ATIVO = "A";

	@SuppressWarnings("unchecked")
	public SituacaoColaborador()
	{
		put(ATIVO, "Ativo");
		put(TODOS, "Todos");
		put(DESLIGADO, "Desligado");
	}
}
