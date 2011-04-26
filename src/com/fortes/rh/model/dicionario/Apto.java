package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Apto extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 4215324525333421727L;
	
	public static final char SIM = 'S';
	public static final char NAO = 'N';
	public static final char INDIFERENTE = 'I';

	public Apto()
	{
		put(INDIFERENTE, "");
		put(SIM, "Sim");
		put(NAO, "Não");
	}

	public static String getDescApto(Character apto)
	{
		if (apto == null || apto == 'I' )
			return "-";

		if (apto == 'S')
			return "Sim";
		else
			return "Não";
	}
}
