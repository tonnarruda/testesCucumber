package com.fortes.rh.test.factory.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;

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
	
	public static FaixaSalarialHistorico getEntity(FaixaSalarial faixaSalarial, Date data, Integer status)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = getEntity();
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		faixaSalarialHistorico.setData(data);
		faixaSalarialHistorico.setStatus(status);
		
		return faixaSalarialHistorico;
	}
	
	public static FaixaSalarialHistorico getEntity(Long id, Indice indice, int status, Date data)
	{
		FaixaSalarialHistorico faixaSalarialHistorico = getEntity(id);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setStatus(status);;
		faixaSalarialHistorico.setData(data);
		
		return faixaSalarialHistorico;
	}

	public static Collection<FaixaSalarialHistorico> getCollection()
	{
		Collection<FaixaSalarialHistorico> faixaSalarialHistoricos = new ArrayList<FaixaSalarialHistorico>();
		faixaSalarialHistoricos.add(getEntity());

		return faixaSalarialHistoricos;
	}
}
