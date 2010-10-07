package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.AreaFormacao;

public class AreaFormacaoFactory
{
	public static AreaFormacao getEntity()
	{
		AreaFormacao areaFormacao = new AreaFormacao();

		areaFormacao.setId(null);
		areaFormacao.setNome("nome da area de formação");

		return areaFormacao;
	}

	public static Collection<AreaFormacao> getCollection()
	{
		Collection<AreaFormacao> areaFormacaos = new ArrayList<AreaFormacao>();
		areaFormacaos.add(getEntity());

		return areaFormacaos;
	}
}
