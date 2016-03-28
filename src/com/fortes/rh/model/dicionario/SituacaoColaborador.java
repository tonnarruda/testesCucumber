package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class SituacaoColaborador extends LinkedHashMap<String, String>
{
	public static final String TODOS = "T";
	public static final String DESLIGADO = "D";
	public static final String ATIVO = "A";

	public SituacaoColaborador()
	{
		put(ATIVO, "Ativo");
		put(DESLIGADO, "Desligado");
		put(TODOS, "Todos");
	}

	public static String getTodos() {
		return TODOS;
	}

	public static String getDesligado() {
		return DESLIGADO;
	}

	public static String getAtivo() {
		return ATIVO;
	}
}
