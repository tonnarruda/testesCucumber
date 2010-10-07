package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("unchecked")
public class OrigemCandidato extends LinkedHashMap
{
	private static final long serialVersionUID = -6993560202794313685L;
	
	public static final char CADASTRADO = 'C';
	public static final char BDS = 'B';
	public static final char EXTERNO = 'E';
	public static final char IMPORTADO = 'I';
	public static final char OFFICE = 'O';

	public OrigemCandidato()
	{
		put(CADASTRADO, "Cadastrado Manualmente");
		put(BDS, "BDS");
		put(IMPORTADO, "Importado");
		put(EXTERNO, "Módulo Externo");
		put(OFFICE, "MS Office");
	}
}
