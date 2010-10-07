package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class FiltrosRelatorio extends LinkedHashMap
{
	@SuppressWarnings("unchecked")
	public FiltrosRelatorio()
	{
		put("1","Área Organizacional");
		put("2","Grupo Ocupacional");
		put("3","Área Organizacional E Grupo Ocupacional");
		put("4","Área Organizacional OU Grupo Ocupacional");
	}
}
