package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.GastoEmpresaItem;

public class GastoEmpresaItemFactory
{
	public static GastoEmpresaItem getEntity()
	{
		GastoEmpresaItem gastoEmpresaItem = new GastoEmpresaItem();
		gastoEmpresaItem.setId(null);

		return gastoEmpresaItem;
	}

	public static GastoEmpresaItem getEntity(Long id)
	{
		GastoEmpresaItem gastoEmpresaItem = getEntity();
		gastoEmpresaItem.setId(id);

		return gastoEmpresaItem;
	}

	public static Collection<GastoEmpresaItem> getCollection()
	{
		Collection<GastoEmpresaItem> collection = new ArrayList<GastoEmpresaItem>();
		collection.add(getEntity());
		return collection;
	}
}
