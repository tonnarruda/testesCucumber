package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class EscolaridadeACPessoal extends LinkedHashMap<String, String> {
	private static final long serialVersionUID = -3100048869132122135L;

	public static final String ANALFABETO = "01";
	public static final String PRIMARIO_EM_ANDAMENTO = "02";
	public static final String PRIMARIO_COMPLETO = "03";
	public static final String GINASIO_EM_ANDAMENTO = "04";
	public static final String GINASIO_COMPLETO = "05";
	public static final String COLEGIAL_EM_ANDAMENTO = "06";
	public static final String COLEGIAL_COMPLETO = "07";
	public static final String SUPERIOR_EM_ANDAMENTO = "08";
	public static final String SUPERIOR_COMPLETO = "09";	
	public static final String MESTRADO = "10";
	public static final String DOUTORADO = "11";

	public EscolaridadeACPessoal() {
		put(ANALFABETO, "Analfabeto");
		put(PRIMARIO_EM_ANDAMENTO, "Até o 5ºano incompleto do ensino fundamental");
		put(PRIMARIO_COMPLETO, "5º ano completo do ensino fundamental");
		put(GINASIO_EM_ANDAMENTO, "Do 6º ao 9º ano do ensino fundamental incompleto");
		put(GINASIO_COMPLETO, "Ensino fundamental completo");
		put(COLEGIAL_EM_ANDAMENTO, "Ensino médio incompleto");
		put(COLEGIAL_COMPLETO, "Ensino médio completo");
		put(SUPERIOR_EM_ANDAMENTO, "Educação superior incompleto");
		put(SUPERIOR_COMPLETO, "Educação superior completo");
		put(MESTRADO, "Mestrado completo");
		put(DOUTORADO, "Doutorado completo");
	}
}