package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoClinica extends LinkedHashMap
{
	public static final String CLINICA = "01";
	public static final String MEDICO = "02";
	public static final String OUTRO = "03";

	@SuppressWarnings("unchecked")
	public TipoClinica()
	{
		put(CLINICA, "Clínica");
		put(MEDICO, "Médico");
		put(OUTRO, "Outro");
	}
	
	public static String getDescricao(String tipo)
	{
		if(tipo == null)
			return "";
		
		return (String) new TipoClinica().get(tipo);
	}
}