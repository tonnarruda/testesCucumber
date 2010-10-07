package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.GastoEmpresa;

public class GastoEmpresaFactory
{
	public static GastoEmpresa getEntity()
	{
		GastoEmpresa gastoEmpresa = new GastoEmpresa();
		gastoEmpresa.setId(null);

		return gastoEmpresa;
	}

	public static GastoEmpresa getEntity(Long id)
	{
		GastoEmpresa gastoEmpresa = getEntity();
		gastoEmpresa.setId(id);

		return gastoEmpresa;
	}

	public static Collection<GastoEmpresa> getCollection()
	{
		Collection<GastoEmpresa> collection = new ArrayList<GastoEmpresa>();
		collection.add(getEntity());
		return collection;
	}
}
