package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Colaborador;
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
	
	public static ColaboradorIdioma getEntity(Long id)
	{
		ColaboradorIdioma colaboradorIdioma = new ColaboradorIdioma();
		colaboradorIdioma.setId(id);
		
		return colaboradorIdioma;
	}
	
	public static ColaboradorIdioma getEntity(Long id, Colaborador colaborador)
	{
		ColaboradorIdioma colaboradorIdioma = new ColaboradorIdioma();
		colaboradorIdioma.setId(id);
		colaboradorIdioma.setColaborador(colaborador);
		
		return colaboradorIdioma;
	}
}
