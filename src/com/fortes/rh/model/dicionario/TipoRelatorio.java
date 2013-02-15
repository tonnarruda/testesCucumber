package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;


@SuppressWarnings("serial")
public class TipoRelatorio extends LinkedHashMap<Character, String>
{
	public static final char PDF = 'P';
	public static final char XLS= 'X';

	public TipoRelatorio() 
	{
		put(PDF, "Documento PDF");
		put(XLS, "Planilha");
	}
}