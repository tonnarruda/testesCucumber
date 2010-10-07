package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoFormacao extends LinkedHashMap
{
	private static final char CONCLUIDO = 'C';
	private static final char ANDAMENTO = 'A';
	private static final char INCOMPLETA = 'I';

	@SuppressWarnings("unchecked")
	public SituacaoFormacao()
	{
		put(CONCLUIDO, "Concluído");
		put(ANDAMENTO, "Em andamento");
		put(INCOMPLETA, "Incompleta");
	}
}
