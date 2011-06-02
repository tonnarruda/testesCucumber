package com.fortes.rh.model.dicionario;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Escolaridade extends LinkedHashMap<String, String> {
	private static final long serialVersionUID = -8052661735365734073L;

	public static final String ANALFABETO = "01";
	public  static final String PRIMARIO_EM_ANDAMENTO = "02";
	public  static final String PRIMARIO_COMPLETO = "03";
	public  static final String GINASIO_EM_ANDAMENTO = "04";
	public  static final String GINASIO_COMPLETO = "05";
	public  static final String COLEGIAL_EM_ANDAMENTO = "06";
	public  static final String COLEGIAL_COMPLETO = "07";
	public  static final String SUPERIOR_EM_ANDAMENTO = "08";
	public  static final String SUPERIOR_COMPLETO = "09";
	public  static final String MESTRADO = "10";
	public  static final String DOUTORADO = "11";
	public  static final String ESPECIALIZACAO = "12";

	public Escolaridade() {
		put(ANALFABETO, "Sem escolaridade");
		put(PRIMARIO_EM_ANDAMENTO, "Ensino Fundamental em andamento (até 5ºano)");
		put(PRIMARIO_COMPLETO, "Ensino Fundamental completo (até 5ºano)");
		put(GINASIO_EM_ANDAMENTO, "Ensino Fundamental em andamento (até 9ºano)");
		put(GINASIO_COMPLETO, "Ensino Fundamental completo (até 9ºano)");
		put(COLEGIAL_EM_ANDAMENTO, "Ensino Médio em andamento");
		put(COLEGIAL_COMPLETO, "Ensino Médio completo");
		put(SUPERIOR_EM_ANDAMENTO, "Superior em andamento");
		put(SUPERIOR_COMPLETO, "Superior completo");
		put(ESPECIALIZACAO, "Especialização");
		put(MESTRADO, "Mestrado");
		put(DOUTORADO, "Doutorado");
	}

	public static String bindF2rh(String chave) 
	{
		try {
			HashMap<String, String> convert = new HashMap<String, String>();
			convert.put("Não Alfabetizado", ANALFABETO);
			convert.put("Ensino Fundamental (1º Grau) em andamento", GINASIO_EM_ANDAMENTO);
			convert.put("Ensino Fundamental (1º Grau) completo", GINASIO_COMPLETO);
			convert.put("Ensino Médio (2º Grau) em andamento", COLEGIAL_EM_ANDAMENTO);
			convert.put("Ensino Médio Técnico (2º Grau) em andamento", COLEGIAL_EM_ANDAMENTO);
			convert.put("Ensino Médio (2º Grau) completo", COLEGIAL_COMPLETO);
			convert.put("Ensino Médio Técnico (2º Grau) completo", COLEGIAL_COMPLETO);
			convert.put("Superior em andamento", SUPERIOR_EM_ANDAMENTO);
			convert.put("Superior completo", SUPERIOR_COMPLETO);
			convert.put("Pós-graduação Incompleta", SUPERIOR_COMPLETO);
			convert.put("Pós-graduação Completa", ESPECIALIZACAO);
			convert.put("Mestrado em andamento", SUPERIOR_COMPLETO);
			convert.put("Mestrado completo", MESTRADO);
			convert.put("Doutorado em andamento", MESTRADO);
			convert.put("Doutorado completo", DOUTORADO);
			
			return convert.get(chave);
			
		} catch (Exception e) {
			return "";
		}
		
	}

	public static String getEscolaridadeF2rh(String chave) {
		try {
			HashMap<String, String> convert = new HashMap<String, String>();
			convert.put(ANALFABETO, "Não Alfabetizado");
			convert.put(PRIMARIO_EM_ANDAMENTO, "Ensino Fundamental em andamento");
			convert.put(PRIMARIO_COMPLETO, "Ensino Fundamental completo");
			convert.put(GINASIO_EM_ANDAMENTO, "Ensino Fundamental em andamento");
			convert.put(GINASIO_COMPLETO, "Ensino Fundamental completo");
			convert.put(COLEGIAL_EM_ANDAMENTO, "Ensino Médio em andamento");
			convert.put(COLEGIAL_COMPLETO, "Ensino Médio completo");
			convert.put(SUPERIOR_EM_ANDAMENTO, "Superior em andamento");
			convert.put(SUPERIOR_COMPLETO, "Superior completo");
			convert.put(ESPECIALIZACAO, "Pós-graduação");
			convert.put(MESTRADO, "Mestrado");
			convert.put(DOUTORADO, "Doutorado");

			return convert.get(chave);

		} catch (Exception e) {
			return "";
		}
	}
}