package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class ParecerEmpresa extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;
	public static final String VIAVEL = "V";
	public static final String INVIAVEL = "I";
	public static final String ESTUDO = "E";

	private static ParecerEmpresa prioridade;

	public static ParecerEmpresa getInstance()
	{
		if (prioridade == null)
			prioridade = new ParecerEmpresa();

		return prioridade;
	}

	@SuppressWarnings("unchecked")
	private ParecerEmpresa()
	{
		put(VIAVEL, "Viável");
		put(INVIAVEL, "Inviável");
		put(ESTUDO, "Em estudo");
	}
}