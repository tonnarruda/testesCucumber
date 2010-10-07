package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoAvaliacao extends LinkedHashMap
{
	public static final int PESQUISA = 0;
	public static final int AVALIACAO = 1;
	public static final int ENTREVISTA = 2;

	@SuppressWarnings("unchecked")
	public TipoAvaliacao()
	{
		put(PESQUISA,"Pesquisa");
		put(AVALIACAO, "Avaliação");
		put(ENTREVISTA, "Entrevista");
	}
}
