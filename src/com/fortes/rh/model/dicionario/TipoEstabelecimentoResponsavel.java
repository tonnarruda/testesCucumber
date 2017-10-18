package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoEstabelecimentoResponsavel extends LinkedHashMap<String,String>
{
	public static final String TODOS = "TODOS";
	public static final String ALGUNS = "ALGUNS";

	public TipoEstabelecimentoResponsavel()
	{
		put(TODOS , "Todos estabelecimentos");
		put(ALGUNS, "Alguns estabelecimentos");
	}

	public static String getTodos() {
		return TODOS;
	}

	public static String getAlguns() {
		return ALGUNS;
	}
	
	public static String getDescricao(String tipo)
	{
		TipoEstabelecimentoResponsavel tipoEstabelecimentoResponsavel = new TipoEstabelecimentoResponsavel();
		return tipoEstabelecimentoResponsavel.get(tipo);
	}

}
