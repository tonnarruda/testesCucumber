package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Vinculo extends LinkedHashMap<String,String>
{
	public static final String EMPREGO = "E";
	public static final String ESTAGIO = "S";
	public static final String APRENDIZ = "A";
	public static final String TEMPORARIO = "T";
	public static final String SOCIO = "O";

	public Vinculo()
	{
		put(EMPREGO, "Emprego");
		put(ESTAGIO, "Estágio");
		put(APRENDIZ, "Aprendiz");
		put(TEMPORARIO, "Temporário");
		put(SOCIO, "Sócio");
	}
}
