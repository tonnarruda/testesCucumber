package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.IndiceHistorico;

public class IndiceHistoricoFactory
{
	public static IndiceHistorico getEntity()
	{
		IndiceHistorico indiceHistorico = new IndiceHistorico();

		indiceHistorico.setId(null);

		return indiceHistorico;
	}


	public static IndiceHistorico getEntity(Long id)
	{
		IndiceHistorico indiceHistorico = getEntity();
		indiceHistorico.setId(id);

		return indiceHistorico;
	}

	public static Collection<IndiceHistorico> getCollection()
	{
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(getEntity());

		return indiceHistoricos;
	}

	public static Collection<IndiceHistorico> getCollection(Long id)
	{
		Collection<IndiceHistorico> indiceHistoricos = new ArrayList<IndiceHistorico>();
		indiceHistoricos.add(getEntity(id));

		return indiceHistoricos;
	}
}
