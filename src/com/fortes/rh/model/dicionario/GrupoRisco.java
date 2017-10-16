package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class GrupoRisco extends LinkedHashMap<String, String>
{
	private static final long serialVersionUID = 9009590293178247206L;
	
	public static final String FISICO = "01";
	public static final String QUIMICO = "02";
	public static final String BIOLOGICO = "03";
	public static final String ERGONOMICO = "04";
	public static final String ACIDENTE = "05";
	
	private static GrupoRisco instance;
	
	public static GrupoRisco getInstance()
	{
		if (instance == null)
			instance = new GrupoRisco();
		
		return instance;
	}

	private GrupoRisco()
	{
		put(FISICO, "Físico");
		put(QUIMICO, "Químico");
		put(BIOLOGICO, "Biológico");
		put(ERGONOMICO, "Ergonômico");
		put(ACIDENTE, "Acidente");
	}
}