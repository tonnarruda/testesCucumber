package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class StatusCandidatoSolicitacao extends LinkedHashMap<Character, String>
{
	private static final long serialVersionUID = 4215324525333421727L;
	
	public static final char CONTRATADO = 'C';
	public static final char PROMOVIDO = 'P';
	public static final char INDIFERENTE = 'I';

	public StatusCandidatoSolicitacao()
	{
		put(INDIFERENTE, "Indiferente");
		put(CONTRATADO, "Contratado");
		put(PROMOVIDO, "Promovido");
	}

	public static String getDescStatusCandidatoSolicitacao(char statusCandidatoSolicitacao)
	{
		if (statusCandidatoSolicitacao == 'C')
			return "Contratado";

		if (statusCandidatoSolicitacao == 'P')
			return "Promovido";
		
		return "Indiferente";
	}
}
