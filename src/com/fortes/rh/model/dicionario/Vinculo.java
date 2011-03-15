package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Vinculo extends LinkedHashMap
{
	public static final String EMPREGO = "E";
	public static final String ESTAGIO = "S";
	public static final String APRENDIZ = "A";
	public static final String TEMPORARIO = "T";

	@SuppressWarnings("unchecked")
	public Vinculo()
	{
		put(EMPREGO, "Emprego");
		put(ESTAGIO, "Estágio");
		put(APRENDIZ, "Aprendiz");
		put(TEMPORARIO, "Temporário");
	}
}
