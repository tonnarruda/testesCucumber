package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.ColaboradorIdioma;

public class ColaboradorIdiomaFactory
{
	public static ColaboradorIdioma getEntity()
	{
		ColaboradorIdioma colaboradorIdioma = new ColaboradorIdioma();
		colaboradorIdioma.setId(null);
		colaboradorIdioma.setIdioma(null);
		colaboradorIdioma.setNivel('A');
		colaboradorIdioma.setColaborador(null);

		return colaboradorIdioma;
	}
}
