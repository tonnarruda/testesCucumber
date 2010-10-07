package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class Mes extends LinkedHashMap
{
	public static final int JANEIRO = 1;
	public static final int FEVEREIRO = 2;
	public static final int MARCO = 3;
	public static final int ABRIL = 4;
	public static final int MAIO = 5;
	public static final int JUNHO = 6;
	public static final int JULHO = 7;
	public static final int AGOSTO = 8;
	public static final int SETEMBRO = 9;
	public static final int OUTUBRO = 10;
	public static final int NOVEMBRO = 11;
	public static final int DEZEMBRO = 12;
	
	@SuppressWarnings("unchecked")
	public Mes() 
	{
		put(JANEIRO, "Janeiro");
		put(FEVEREIRO, "Fevereiro");
		put(MARCO, "Março");
		put(ABRIL, "Abril");
		put(MAIO, "Maio");
		put(JUNHO, "Junho");
		put(JULHO, "Julho");
		put(AGOSTO, "Agosto");
		put(SETEMBRO, "Setembro");
		put(OUTUBRO, "Outubro");
		put(NOVEMBRO, "Novembro");
		put(DEZEMBRO, "Dezembro");
	}
}