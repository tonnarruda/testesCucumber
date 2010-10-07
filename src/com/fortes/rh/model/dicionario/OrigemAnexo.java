package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class OrigemAnexo extends LinkedHashMap
{
	public static final char LTCAT = 'A';
	public static final char PPRA = 'B';
	public static final char AnexoCandidato = 'C';
	public static final char AnexoColaborador = 'D';

	@SuppressWarnings("unchecked")
	public OrigemAnexo()
	{
		super();
		put(LTCAT, "LTCAT");
		put(PPRA, "PPRA");
		put(AnexoCandidato, "AnexoCandidato");
		put(AnexoColaborador, "AnexoColaborador");
	}
}
