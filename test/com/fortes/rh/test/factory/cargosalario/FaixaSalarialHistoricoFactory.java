package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;

public class FaixaSalarialHistoricoFactory
{
	public static FaixaSalarialHistorico getEntity()
	{
		FaixaSalarialHistorico faixaSalarialHistorico = new FaixaSalarialHistorico();

		faixaSalarialHistorico.setId(null);
		faixaSalarialHistorico.setTipo(1);
		faixaSalarialHistorico.setQuantidade(2.0);

		return faixaSalarialHistorico;
	}

	public static FaixaSalarialHistorico getEntity(Long id)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = getEntity();
		faixaSalarialHistorico.setId(id);

		return faixaSalarialHistorico;
	}

	public static Collection<FaixaSalarialHistorico> getCollection()
	{
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(getEntity());

		return faixaSalarialHistoricos;
	}
}
