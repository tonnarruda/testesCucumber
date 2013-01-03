package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NivelIdioma extends LinkedHashMap
{
	private static final long serialVersionUID = 4151982962011616197L;
	
	private static final char BASICO = 'B';
	private static final char INTERMEDIARIO = 'I';
	private static final char AVANCADO = 'A';

	public NivelIdioma()
	{
		put(BASICO, "Básico");
		put(INTERMEDIARIO, "Intermediário");
		put(AVANCADO, "Avançado");
	}
}