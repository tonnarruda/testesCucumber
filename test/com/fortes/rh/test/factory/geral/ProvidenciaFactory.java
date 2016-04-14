package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.Providencia;

public class ProvidenciaFactory
{
	public static Providencia getEntity()
	{
		Providencia providencia = new Providencia();
		providencia.setId(null);
		return providencia;
	}

	public static Providencia getEntity(Long id)
	{
		Providencia providencia = getEntity();
		providencia.setId(id);

		return providencia;
	}
	
	public static Providencia getEntity(String descricao)
	{
		Providencia providencia = getEntity();
		providencia.setDescricao(descricao);;
		return providencia;
	}

	public static Collection<Providencia> getCollection()
	{
		Collection<Providencia> providencias = new ArrayList<Providencia>();
		providencias.add(getEntity());

		return providencias;
	}
	
	public static Collection<Providencia> getCollection(Long id)
	{
		Collection<Providencia> providencias = new ArrayList<Providencia>();
		providencias.add(getEntity(id));
		
		return providencias;
	}
}
