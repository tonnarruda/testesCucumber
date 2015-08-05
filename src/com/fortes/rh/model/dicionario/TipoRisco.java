package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TipoRisco extends LinkedHashMap<String, String> {

	public static final String FISICO = "01";
	public static final String QUIMICO = "02";
	public static final String BIOLOGICO = "03";
	public static final String ERGONOMICO = "04";
	public static final String ACIDENTE = "05";
}