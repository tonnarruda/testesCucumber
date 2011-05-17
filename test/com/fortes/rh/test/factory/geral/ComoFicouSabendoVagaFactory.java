package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.ComoFicouSabendoVaga;

public class ComoFicouSabendoVagaFactory
{
	public static ComoFicouSabendoVaga getEntity()
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = new ComoFicouSabendoVaga();
		comoFicouSabendoVaga.setId(null);
		return comoFicouSabendoVaga;
	}

	public static ComoFicouSabendoVaga getEntity(Long id)
	{
		ComoFicouSabendoVaga comoFicouSabendoVaga = getEntity();
		comoFicouSabendoVaga.setId(id);

		return comoFicouSabendoVaga;
	}

	public static Collection<ComoFicouSabendoVaga> getCollection()
	{
		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = new ArrayList<ComoFicouSabendoVaga>();
		comoFicouSabendoVagas.add(getEntity());

		return comoFicouSabendoVagas;
	}
	
	public static Collection<ComoFicouSabendoVaga> getCollection(Long id)
	{
		Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas = new ArrayList<ComoFicouSabendoVaga>();
		comoFicouSabendoVagas.add(getEntity(id));
		
		return comoFicouSabendoVagas;
	}
}
