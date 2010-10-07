package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoEntidade extends LinkedHashMap<String,String>
{
	private static final long serialVersionUID = 1L;
	
	public static final String AREAS = "1";
	public static final String CARGOS = "2";
	public static final String CONHECIMENTOS = "3";
	public static final String AREAS_INTERESSE = "4";
	public static final String TIPOS_OCORRENCIA = "5";
	public static final String EPIS = "6";
	
	public TipoEntidade()
	{
		put(AREAS, "Áreas Organizacionais");
		put(CONHECIMENTOS, "Conhecimentos");
		put(AREAS_INTERESSE, "Áreas de Interesse");
		put(CARGOS, "Cargos");
		put(TIPOS_OCORRENCIA, "Tipos de Ocorrencia");
		put(EPIS, "EPIs");
	}
}