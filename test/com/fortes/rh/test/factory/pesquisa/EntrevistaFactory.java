package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.Entrevista;

public class EntrevistaFactory
{
	public static Entrevista getEntity()
	{
		Entrevista entrevista = new Entrevista();

		entrevista.setId(null);
		entrevista.setQuestionario(null);

		return entrevista;
	}

	public static Entrevista getEntity(Long id)
	{
		Entrevista entrevista = getEntity();
		entrevista.setId(id);

		return entrevista;
	}

	public static Collection<Entrevista> getCollection()
	{
		Collection<Entrevista> entrevistas = new ArrayList<Entrevista>();
		entrevistas.add(getEntity(1L));

		return entrevistas;
	}
}
