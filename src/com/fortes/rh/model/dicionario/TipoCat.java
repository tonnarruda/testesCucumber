package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoCat extends LinkedHashMap<Long, String>{
	public static final Long INICIAL = 1L;
	public static final Long REABERTURA = 2L;
	public static final Long OBITO = 3L;
	
	public TipoCat()
	{
		put(INICIAL, "Inicial");
		put(REABERTURA, "Reabertura");
		put(OBITO, "Comunicação de Óbito");
	}
}
