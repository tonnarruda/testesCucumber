package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoAcidente extends LinkedHashMap<Integer, String>
{
	private static final long serialVersionUID = 4215324525333421727L;
	
	public static final int tipico = 1;
	public static final int trajeto = 2;
	public static final int doenca_trabalho = 3;

	public TipoAcidente()
	{
		put(tipico, "Típico");
		put(trajeto, "Trajeto");
		put(doenca_trabalho, "Doença do Trabalho");
	}
}
