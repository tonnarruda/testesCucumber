package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.FichaMedica;

public class FichaMedicaFactory
{
	public static FichaMedica getEntity()
	{
		FichaMedica fichaMedica = new FichaMedica();

		fichaMedica.setId(null);
		fichaMedica.setQuestionario(null);

		return fichaMedica;
	}

	public static FichaMedica getEntity(Long id)
	{
		FichaMedica fichaMedica = getEntity();
		fichaMedica.setId(id);

		return fichaMedica;
	}

	public static Collection<FichaMedica> getCollection()
	{
		Collection<FichaMedica> fichaMedicas = new ArrayList<FichaMedica>();
		fichaMedicas.add(getEntity(1L));

		return fichaMedicas;
	}
}
