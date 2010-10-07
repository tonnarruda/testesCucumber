package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Vinculo extends LinkedHashMap
{
	public static final char EMPREGO = 'E';
	public static final char ESTAGIO = 'S';
	public static final char APRENDIZ = 'A';
	public static final char TEMPORARIO = 'T';

	@SuppressWarnings("unchecked")
	public Vinculo()
	{
		put(EMPREGO, "Emprego");
		put(ESTAGIO, "Estágio");
		put(APRENDIZ, "Aprendiz");
		put(TEMPORARIO, "Temporário");
	}
}
