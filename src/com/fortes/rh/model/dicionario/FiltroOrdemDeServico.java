package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class FiltroOrdemDeServico extends LinkedHashMap<String, String>
{
	public static final String TODOS = "T";
	public static final String SEM_ORDEM_DE_SERVICO = "S";
	public static final String COM_ORDEM_DE_SERVICO = "C";

	public FiltroOrdemDeServico()
	{
		put(COM_ORDEM_DE_SERVICO, "Com ordem de serviço");
		put(SEM_ORDEM_DE_SERVICO, "Sem ordem de serviço");
		put(TODOS, "Todos");
	}

	public static String getTodos() {
		return TODOS;
	}

	public static String getSemOrdemDeServico() {
		return SEM_ORDEM_DE_SERVICO;
	}

	public static String getComOrdemDeServico() {
		return COM_ORDEM_DE_SERVICO;
	}
}
