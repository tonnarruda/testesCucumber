package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoDeExposicao extends LinkedHashMap
{
	public static final int HABITUAL = 1;
	public static final int INTERMITENTE = 2;
	public static final int EVENTUAL = 3;

	@SuppressWarnings("unchecked")
	public TipoDeExposicao()
	{
		put(HABITUAL, "Habitual");
		put(INTERMITENTE, "Intermitente");
		put(EVENTUAL, "Eventual");
	}
}
