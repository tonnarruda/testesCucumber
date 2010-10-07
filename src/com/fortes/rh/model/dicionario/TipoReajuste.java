package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoReajuste extends LinkedHashMap 
{
	public static final String PORCENTAGEM = "P";
	public static final String VALOR = "V";
	
	@SuppressWarnings("unchecked")
	public TipoReajuste() 
	{
		super();
		put(PORCENTAGEM, "%");
		put(VALOR, "R$");
	}
}

