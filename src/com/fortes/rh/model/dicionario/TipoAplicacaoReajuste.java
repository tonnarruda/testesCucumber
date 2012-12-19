package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoAplicacaoReajuste extends LinkedHashMap 
{
	public static final String PORCENTAGEM = "P";
	public static final String VALOR = "V";
	
	@SuppressWarnings("unchecked")
	public TipoAplicacaoReajuste() 
	{
		super();
		put(PORCENTAGEM, "%");
		put(VALOR, "R$");
	}
}

