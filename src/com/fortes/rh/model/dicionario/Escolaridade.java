package com.fortes.rh.model.dicionario;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Escolaridade extends LinkedHashMap<String, String> {
	private static final long serialVersionUID = -8052661735365734073L;

	public static final String ANALFABETO = "01";
	public  static final String PRIMARIO_INCOMPLETO = "02";
	public  static final String PRIMARIO_COMPLETO = "03";
	public  static final String GINASIO_INCOMPLETO = "04";
	public  static final String GINASIO_COMPLETO = "05";
	public  static final String COLEGIAL_INCOMPLETO = "06";
	public  static final String COLEGIAL_COMPLETO = "07";
	public  static final String SUPERIOR_INCOMPLETO = "08";
	public  static final String SUPERIOR_COMPLETO = "09";
	public  static final String MESTRADO = "10";
	public  static final String DOUTORADO = "11";
	public  static final String ESPECIALIZACAO = "12";

	public Escolaridade() {
		put(ANALFABETO, "Sem escolaridade");
		put(PRIMARIO_INCOMPLETO, "Ensino Fundamental Incompleto (até 5ºano)");
		put(PRIMARIO_COMPLETO, "Ensino Fundamental Completo (até 5ºano)");
		put(GINASIO_INCOMPLETO, "Ensino Fundamental Incompleto (até 9ºano)");
		put(GINASIO_COMPLETO, "Ensino Fundamental Completo (até 9ºano)");
		put(COLEGIAL_INCOMPLETO, "Ensino Médio Incompleto");
		put(COLEGIAL_COMPLETO, "Ensino Médio Completo");
		put(SUPERIOR_INCOMPLETO, "Superior Incompleto");
		put(SUPERIOR_COMPLETO, "Superior Completo");
		put(ESPECIALIZACAO, "Especialização");
		put(MESTRADO, "Mestrado");
		put(DOUTORADO, "Doutorado");
	}

	public static String bindF2rh(String chave) 
	{
		try {
			HashMap<String, String> convert = new HashMap<String, String>();
			convert.put("Não Alfabetizado", ANALFABETO);
			convert.put("Ensino Fundamental (1º Grau) Incompleto", GINASIO_INCOMPLETO);
			convert.put("Ensino Fundamental (1º Grau) Completo", GINASIO_COMPLETO);
			convert.put("Ensino Médio (2º Grau) Incompleto", COLEGIAL_INCOMPLETO);
			convert.put("Ensino Médio Técnico (2º Grau) Incompleto", COLEGIAL_INCOMPLETO);
			convert.put("Ensino Médio (2º Grau) Completo", COLEGIAL_COMPLETO);
			convert.put("Ensino Médio Técnico (2º Grau) Completo", COLEGIAL_COMPLETO);
			convert.put("Superior Incompleto", SUPERIOR_INCOMPLETO);
			convert.put("Superior Completo", SUPERIOR_COMPLETO);
			convert.put("Pós-graduação Incompleta", SUPERIOR_COMPLETO);
			convert.put("Pós-graduação Completa", ESPECIALIZACAO);
			convert.put("Mestrado Incompleto", SUPERIOR_COMPLETO);
			convert.put("Mestrado Completo", MESTRADO);
			convert.put("Doutorado Incompleto", MESTRADO);
			convert.put("Doutorado Completo", DOUTORADO);
			
			return convert.get(chave);
			
		} catch (Exception e) {
			return "";
		}
		
	}

	public static String getEscolaridadeF2rh(String chave) {
		try {
			HashMap<String, String> convert = new HashMap<String, String>();
			convert.put(ANALFABETO, "Não Alfabetizado");
			convert.put(PRIMARIO_INCOMPLETO, "Ensino Fundamental Incompleto");
			convert.put(PRIMARIO_COMPLETO, "Ensino Fundamental Completo");
			convert.put(GINASIO_INCOMPLETO, "Ensino Fundamental Incompleto");
			convert.put(GINASIO_COMPLETO, "Ensino Fundamental Completo");
			convert.put(COLEGIAL_INCOMPLETO, "Ensino Médio Incompleto");
			convert.put(COLEGIAL_COMPLETO, "Ensino Médio Completo");
			convert.put(SUPERIOR_INCOMPLETO, "Superior Incompleto");
			convert.put(SUPERIOR_COMPLETO, "Superior Completo");
			convert.put(ESPECIALIZACAO, "Pós-graduação");
			convert.put(MESTRADO, "Mestrado");
			convert.put(DOUTORADO, "Doutorado");

			return convert.get(chave);

		} catch (Exception e) {
			return "";
		}
	}
}