package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class SexoCadastro extends LinkedHashMap
{
	private static final char MASCULINO = 'M';
	private static final char FEMININO = 'F';

	@SuppressWarnings("unchecked")
	public SexoCadastro()
	{
		put(MASCULINO, "Masculino");
		put(FEMININO, "Feminino");
	}
}
