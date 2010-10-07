package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoClinica extends LinkedHashMap
{
	public static final String CLINICA = "01";
	public static final String MEDICO = "02";

	@SuppressWarnings("unchecked")
	public TipoClinica()
	{
		put(CLINICA, "Clínica");
		put(MEDICO, "Médico");
	}
}