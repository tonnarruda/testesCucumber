package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class PeriodicidadeExposicao extends LinkedHashMap<Character, String>
{
	public static final Character CONTINUA = 'C';
	public static final Character INTERMITENTE = 'I';
	public static final Character EVENTUAL = 'E';

	public PeriodicidadeExposicao()
	{
		put(CONTINUA, "Contínua");
		put(INTERMITENTE, "Intermitente");
		put(EVENTUAL, "Eventual");
	}
}