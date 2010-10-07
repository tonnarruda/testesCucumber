package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoFormacao extends LinkedHashMap
{
	private static final char TECNICO = 'T';
	private static final char GRADUACAO = 'G';
	private static final char ESPECIALIZACAO = 'E';
	private static final char MESTRADO = 'M';
	private static final char DOUTORADO = 'D';
	private static final char POSDOUTORADO = 'P';

	@SuppressWarnings("unchecked")
	public TipoFormacao()
	{
		put(TECNICO, "Técnico");
		put(GRADUACAO, "Graduação");
		put(ESPECIALIZACAO, "Especialização");
		put(MESTRADO, "Mestrado");
		put(DOUTORADO, "Doutorado");
		put(POSDOUTORADO, "Pós-Doutorado");
	}
}
