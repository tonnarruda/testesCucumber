package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Situacao extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String NAO_INICIADO = "NAO_INICIOU";
	public static final String ANDAMENTO = "ANDAMENTO";
	public static final String CONCLUIDO = "CONCLUIDO";

	private static Situacao situacao;

	public static Situacao getInstance()
	{
		if (situacao == null)
			situacao = new Situacao();

		return situacao;
	}

	@SuppressWarnings("unchecked")
	private Situacao()
	{
		put(NAO_INICIADO, "Não Iniciado");
		put(ANDAMENTO, "Em Andamento");
		put(CONCLUIDO, "Concluído");
	}
}