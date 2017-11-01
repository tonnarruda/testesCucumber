package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings({ "serial", "rawtypes" })
public class Lateralidade extends LinkedHashMap{
	
	public static final int NAOAPLICAVEL = 0;
	public static final int ESQUERDA = 1;
	public static final int DIREITA = 2;
	public static final int AMBAS = 3;

	@SuppressWarnings("unchecked")
	public Lateralidade()
	{
		put(NAOAPLICAVEL, "Não aplicável;");
		put(ESQUERDA, "Esquerda");
		put(DIREITA, "Direita");
		put(AMBAS, "Ambas");
	}
	
}
