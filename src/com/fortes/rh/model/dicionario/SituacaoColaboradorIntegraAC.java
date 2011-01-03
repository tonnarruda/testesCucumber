package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoColaboradorIntegraAC extends LinkedHashMap
{
	private static final String TODOS = "T";
	private static final String DESLIGADO = "D";
	private static final String ATIVO = "A";
	private static final String AGUARDANDO = "G";
	private static final String CANCELADO = "C";

	@SuppressWarnings("unchecked")
	public SituacaoColaboradorIntegraAC()
	{
		put(ATIVO, "Ativo");
		put(TODOS, "Todos");
		put(DESLIGADO, "Desligado");
		put(AGUARDANDO, "Aguardando Confirmação");
		put(CANCELADO, "Cancelado");
	}
}
