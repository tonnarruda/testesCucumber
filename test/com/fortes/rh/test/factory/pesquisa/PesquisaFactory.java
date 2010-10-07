package com.fortes.rh.test.factory.pesquisa;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.pesquisa.Pesquisa;

public class PesquisaFactory
{
	public static Pesquisa getEntity()
	{
		Pesquisa pesquisa = new Pesquisa();

		pesquisa.setId(null);
		pesquisa.setQuestionario(null);

		return pesquisa;
	}

	public static Pesquisa getEntity(Long id)
	{
		Pesquisa pesquisa = getEntity();
		pesquisa.setId(id);

		return pesquisa;
	}

	public static Collection<Pesquisa> getCollection()
	{
		Collection<Pesquisa> pesquisas = new ArrayList<Pesquisa>();
		pesquisas.add(getEntity(1L));

		return pesquisas;
	}
}
