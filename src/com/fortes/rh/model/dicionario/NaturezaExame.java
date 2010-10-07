package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class NaturezaExame extends LinkedHashMap
{
	public static final Integer ADMISSIONAL = 1;
	public static final Integer ASSISTENCIAL = 2;
	public static final Integer DEMISSIONAL = 3;
	public static final Integer PERIODICO = 4;
	public static final Integer RETORNO_TRABALHO = 5;
	public static final Integer MUDANCA_FUNCAO = 6;

	@SuppressWarnings("unchecked")
	public NaturezaExame()
	{
		put(ADMISSIONAL, "Admissional");
		put(ASSISTENCIAL, "Assistencial");
		put(DEMISSIONAL, "Demissional");
		put(PERIODICO, "Periódico");
		put(RETORNO_TRABALHO, "Retorno ao Trabalho");
		put(MUDANCA_FUNCAO, "Mudança de Função");
	}
}
