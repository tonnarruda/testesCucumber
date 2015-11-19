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
	public  static final String TECNICO_EM_ANDAMENTO = "08";
	public  static final String TECNICO_COMPLETO = "09";
	public  static final String SUPERIOR_EM_ANDAMENTO = "10";
	public  static final String SUPERIOR_COMPLETO = "11";	
	public  static final String ESPECIALIZACAO = "12";
	public  static final String MESTRADO = "13";
	public  static final String DOUTORADO = "14";

	public Escolaridade() {
		put(ANALFABETO, "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou");
		put(PRIMARIO_EM_ANDAMENTO, "Até o 5º ano incompleto do Ensino Fundamental (antiga 4ª série) ou que se tenha alfabetizado sem ter frequentado escola regular");
		put(PRIMARIO_COMPLETO, "5º ano completo do Ensino Fundamental");
		put(GINASIO_EM_ANDAMENTO, "Do 6º ao 9º ano do Ensino Fundamental incompleto (antiga 5ª à 8ª série)");
		put(GINASIO_COMPLETO, "Ensino Fundamental completo");
		put(COLEGIAL_EM_ANDAMENTO, "Ensino Médio incompleto");
		put(COLEGIAL_COMPLETO, "Ensino Médio completo");
		put(TECNICO_EM_ANDAMENTO, "Educação Técnica incompleta");
		put(TECNICO_COMPLETO, "Educação Técnica completa");
		put(SUPERIOR_EM_ANDAMENTO, "Educação Superior incompleta");
		put(SUPERIOR_COMPLETO, "Educação Superior completa");
		put(ESPECIALIZACAO, "Especialização completa");
		put(MESTRADO, "Mestrado completo");
		put(DOUTORADO, "Doutorado completo");
	}

	public static String bindF2rh(String chave) 
	{
		if(chave == null || chave.isEmpty())
			return ANALFABETO;
		
		try {
			HashMap<String, String> convert = new HashMap<String, String>();
			convert.put("Não Alfabetizado", ANALFABETO);
			convert.put("Ensino Fundamental (1º Grau) Incompleto", GINASIO_EM_ANDAMENTO);
			convert.put("Ensino Fundamental (1º Grau) Completo", GINASIO_COMPLETO);
			convert.put("Ensino Médio (2º Grau) Incompleto", COLEGIAL_EM_ANDAMENTO);
			convert.put("Ensino Médio (2º Grau) Completo", COLEGIAL_COMPLETO);
			convert.put("Ensino Médio Técnico (2º Grau) Incompleto", TECNICO_EM_ANDAMENTO);
			convert.put("Ensino Médio Técnico (2º Grau) Completo", TECNICO_COMPLETO);
			convert.put("Superior Incompleto", SUPERIOR_EM_ANDAMENTO);
			convert.put("Superior Completo", SUPERIOR_COMPLETO);
			convert.put("Pós-graduação Incompleta", ESPECIALIZACAO);
			convert.put("Pós-graduação Completa", ESPECIALIZACAO);
			convert.put("Mestrado Incompleto", MESTRADO);
			convert.put("Mestrado Completo", MESTRADO);
			convert.put("Doutorado Incompleto", DOUTORADO);
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
			convert.put(PRIMARIO_EM_ANDAMENTO, "Ensino Fundamental (1º Grau) Incompleto");
			convert.put(PRIMARIO_COMPLETO, "Ensino Fundamental (1º Grau) Completo");
			convert.put(GINASIO_EM_ANDAMENTO, "Ensino Fundamental (1º Grau) Incompleto");
			convert.put(GINASIO_COMPLETO, "Ensino Fundamental (1º Grau) Completo");
			convert.put(COLEGIAL_EM_ANDAMENTO, "Ensino Médio (2º Grau) Incompleto");
			convert.put(COLEGIAL_COMPLETO, "Ensino Médio (2º Grau) Completo");
			convert.put(TECNICO_EM_ANDAMENTO, "Ensino Médio Técnico (2º Grau) Incompleto");
			convert.put(TECNICO_COMPLETO, "Ensino Médio Técnico (2º Grau) Completo");
			convert.put(SUPERIOR_EM_ANDAMENTO, "Superior Incompleto");
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