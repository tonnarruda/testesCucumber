package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoColaborador extends LinkedHashMap
{
	private static final String TODOS = "T";
	private static final String DESLIGADO = "D";
	private static final String ATIVO = "A";

	@SuppressWarnings("unchecked")
	public SituacaoColaborador()
	{
		put(ATIVO, "Ativo");
		put(TODOS, "Todos");
		put(DESLIGADO, "Desligado");
	}
}
