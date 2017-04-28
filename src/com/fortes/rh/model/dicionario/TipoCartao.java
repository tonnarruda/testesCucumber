package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoCartao extends LinkedHashMap<String, String> 
{
	public static final String ANIVERSARIO = "A";
	public static final String ANO_DE_EMPRESA = "E";
	public static final String BOAS_VINDAS = "B";
	
	public TipoCartao() 
	{
		put(ANIVERSARIO, "Aniversário");
		put(ANO_DE_EMPRESA, "Ano de empresa");
		put(BOAS_VINDAS, "Boas-Vindas");
	}
	
	public static String getDescricao(String tipo)
	{
		TipoCartao tipoCartao = new TipoCartao();
		
		if (!tipoCartao.containsKey(tipo))
			return "";
		
		return tipoCartao.get(tipo);
	}
	
	public static String getAniversario()
	{
		return ANIVERSARIO;
	}

	public static String getAnoDeEmpresa()
	{
		return ANO_DE_EMPRESA;
	}
	
	public static String getBoasVindas()
	{
		return BOAS_VINDAS;
	}
}
