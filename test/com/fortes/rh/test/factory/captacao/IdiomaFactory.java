package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.Idioma;

public class IdiomaFactory
{
	public static Idioma getIdioma()
	{
		Idioma idioma = new Idioma();

		idioma.setId(null);
		idioma.setNome("nome");
		return idioma;
	}
}
