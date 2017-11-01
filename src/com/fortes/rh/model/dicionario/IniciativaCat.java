package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class IniciativaCat extends LinkedHashMap<Long, String>{
	public static final Long REGISTARDOR = 1L;
	public static final Long JUDICIAL = 2L;
	public static final Long ORGAOFISCALIZADOR = 3L;

	public IniciativaCat()
	{
		put(REGISTARDOR, "Iniciativa do Registrador ");
		put(JUDICIAL, "Ordem judicial");
		put(ORGAOFISCALIZADOR, "Determinação de órgão fiscalizador");
	}
}
