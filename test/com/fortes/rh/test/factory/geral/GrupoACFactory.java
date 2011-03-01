package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.GrupoAC;

public class GrupoACFactory
{
	public static GrupoAC getEntity()
	{
		GrupoAC grupoAC = new GrupoAC();
		grupoAC.setId(null);
		grupoAC.setDescricao("Teste");
		grupoAC.setCodigo("999");
		return grupoAC;
	}

	public static GrupoAC getEntity(Long id)
	{
		GrupoAC grupoAC = getEntity();
		grupoAC.setId(id);

		return grupoAC;
	}

	public static Collection<GrupoAC> getCollection()
	{
		Collection<GrupoAC> grupoACs = new ArrayList<GrupoAC>();
		grupoACs.add(getEntity());

		return grupoACs;
	}
	
	public static Collection<GrupoAC> getCollection(Long id)
	{
		Collection<GrupoAC> grupoACs = new ArrayList<GrupoAC>();
		grupoACs.add(getEntity(id));
		
		return grupoACs;
	}
}
