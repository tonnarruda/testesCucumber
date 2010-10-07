package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoReajusteColaborador extends LinkedHashMap
{
	public static final String APROVADO = "A";
	public static final String PENDENTE = "P";
	public static final String REPROVADO = "R";

	@SuppressWarnings("unchecked")
	public TipoReajusteColaborador()
	{
		super();
		put(APROVADO, "A");
		put(PENDENTE, "P");
		put(REPROVADO, "R");
	}
}

