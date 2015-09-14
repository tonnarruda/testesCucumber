package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoRiscoSistema extends LinkedHashMap<String, String> {

	public static final String AMBIENTE = "A";
	public static final String FUNCAO = "F";
	public static final String AMBIENTE_FUNCAO = "AF";
	public static final String NEHUM = "N";

	private static TipoRiscoSistema tipoRiscoSistema;
	
	public static TipoRiscoSistema getInstance()
	{
		if (tipoRiscoSistema == null)
			tipoRiscoSistema = new TipoRiscoSistema(); 

		return tipoRiscoSistema;
	}
	
	private TipoRiscoSistema()
	{
		put(AMBIENTE, "Ambiente");
		put(FUNCAO, "Função");
		put(AMBIENTE_FUNCAO, "Ambiente e Função");
		put(NEHUM, "Nenhum");
	}
}