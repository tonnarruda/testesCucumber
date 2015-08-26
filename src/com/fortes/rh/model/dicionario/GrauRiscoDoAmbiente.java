package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class GrauRiscoDoAmbiente extends LinkedHashMap<Character, Integer>
{
	private static final long serialVersionUID = 3301246497290554279L;
	
	public static final Character PEQUENO = 'P';
	public static final Character MEDIO = 'M';
	public static final Character GRANDE = 'G';

	public GrauRiscoDoAmbiente()
	{
		put(PEQUENO, 1);
		put(MEDIO, 2);
		put(GRANDE, 3);
	}
}
