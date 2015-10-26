package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaFactory
{
	public static ColaboradorAvaliacaoPratica getEntity()
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
		colaboradorAvaliacaoPratica.setId(null);
		return colaboradorAvaliacaoPratica;
	}

	public static ColaboradorAvaliacaoPratica getEntity(Long id)
	{
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica = getEntity();
		colaboradorAvaliacaoPratica.setId(id);

		return colaboradorAvaliacaoPratica;
	}

	public static Collection<ColaboradorAvaliacaoPratica> getCollection()
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(getEntity());

		return colaboradorAvaliacaoPraticas;
	}
	
	public static Collection<ColaboradorAvaliacaoPratica> getCollection(Long id)
	{
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacaoPraticas.add(getEntity(id));
		
		return colaboradorAvaliacaoPraticas;
	}
}
