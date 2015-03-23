package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.cargosalario.Indice;
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

	public static IndiceHistorico getEntity(Indice indice, Date data, Double valor)
	{
		IndiceHistorico indiceHistorico = getEntity();
		indiceHistorico.setIndice(indice);
		indiceHistorico.setData(data);
		indiceHistorico.setValor(valor);
		
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
