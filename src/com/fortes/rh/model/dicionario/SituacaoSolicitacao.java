package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SituacaoSolicitacao extends LinkedHashMap
{
	private static final char CONCLUIDO = 'C';
	private static final char EMANDAMENTO = 'E';
	private static final char ABORTADA = 'A';

	@SuppressWarnings("unchecked")
	public SituacaoSolicitacao()
	{
		put(CONCLUIDO, "Concluído");
		put(EMANDAMENTO, "Em andamento");
		put(ABORTADA, "	Abortada");
	}
}
