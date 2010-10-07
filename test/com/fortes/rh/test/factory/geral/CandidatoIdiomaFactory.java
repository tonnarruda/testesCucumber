package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.captacao.CandidatoIdioma;

public class CandidatoIdiomaFactory
{
	public static CandidatoIdioma getCandidatoIdioma()
	{
		CandidatoIdioma candidatoIdioma = new CandidatoIdioma();
		candidatoIdioma.setId(null);
		candidatoIdioma.setIdioma(null);
		candidatoIdioma.setNivel('A');
		candidatoIdioma.setCandidato(null);

		return candidatoIdioma;
	}
}
