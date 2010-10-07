package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Prioridade extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String ALTA = "A";
	public static final String MEDIA = "M";
	public static final String BAIXA = "B";

	private static Prioridade prioridade;

	public static Prioridade getInstance()
	{
		if (prioridade == null)
			prioridade = new Prioridade();

		return prioridade;
	}

	@SuppressWarnings("unchecked")
	private Prioridade()
	{
		put(ALTA, "Alta");
		put(MEDIA, "Média");
		put(BAIXA, "Baixa");
	}
}
