package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoCartao extends LinkedHashMap<String, String> 
{
	public static final String ANIVERSARIO = "A";
	public static final String ANO_DE_EMPRESA = "E";
	
	public TipoCartao() 
	{
		put(ANIVERSARIO, "Aniversário");
		put(ANO_DE_EMPRESA, "Ano de empresa");
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
}
