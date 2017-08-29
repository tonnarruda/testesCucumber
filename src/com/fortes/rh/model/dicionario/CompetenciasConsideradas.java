package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class CompetenciasConsideradas extends LinkedHashMap<String, String> 
{
	public static final String TODAS = "TODAS";
	public static final String EM_COMUM = "EM_COMUM";
	
	public CompetenciasConsideradas() 
	{
		put(TODAS, "Todas as competências");
		put(EM_COMUM, "Competências em comum");
	}
	
	public static String getTodas() {
		return TODAS;
	}
	
	public static String getEmComum() {
		return EM_COMUM;
	}
	
	public static String getDescricao(String tipo)
	{
		CompetenciasConsideradas competenciasConsideradas = new CompetenciasConsideradas();
		
		return competenciasConsideradas.get(tipo);
	}
	
}
