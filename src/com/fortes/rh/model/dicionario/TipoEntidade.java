package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class TipoEntidade extends LinkedHashMap<String,String>
{
	private static final long serialVersionUID = 1L;
	
	public static final String AREAS = "1";
	public static final String CARGOS = "2";
	public static final String CONHECIMENTOS = "3";
	public static final String HABILIDADES = "4";
	public static final String ATITUDES = "5";
	public static final String AREAS_INTERESSE = "6";
	public static final String TIPOS_OCORRENCIA = "7";
	public static final String EPIS = "8";
	public static final String CURSOSETURMAS = "9";
	public static final String MOTIVOS_DESLIGAMENTO = "10";
	public static final String RISCOS = "11";
	public static final String AMBIENTE = "12";
	
	public TipoEntidade()
	{
		put(CARGOS, "Cargos");
		put(AREAS, "Áreas Organizacionais");
		put(CONHECIMENTOS, "Conhecimentos");
		put(HABILIDADES, "Habilidades");
		put(ATITUDES, "Atitudes");
		put(AREAS_INTERESSE, "Áreas de Interesse");
		put(TIPOS_OCORRENCIA, "Tipos de Ocorrencia");
		put(EPIS, "EPIs");
		put(CURSOSETURMAS, "Cursos e Turma");
		put(MOTIVOS_DESLIGAMENTO, "Motivos de Desligamento");
		put(RISCOS, "Riscos");
		put(AMBIENTE, "Ambiente");
	}
}