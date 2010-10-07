package com.fortes.rh.test.factory.geral;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.geral.CamposExtras;

public class CamposExtrasFactory
{
	public static CamposExtras getEntity()
	{
		CamposExtras camposExtras = new CamposExtras();
		camposExtras.setId(null);
		return camposExtras;
	}

	public static CamposExtras getEntity(Long id)
	{
		CamposExtras camposExtras = getEntity();
		camposExtras.setId(id);

		return camposExtras;
	}

	public static Collection<CamposExtras> getCollection()
	{
		Collection<CamposExtras> camposExtrass = new ArrayList<CamposExtras>();
		camposExtrass.add(getEntity());

		return camposExtrass;
	}
	
	public static Collection<CamposExtras> getCollection(Long id)
	{
		Collection<CamposExtras> camposExtrass = new ArrayList<CamposExtras>();
		camposExtrass.add(getEntity(id));
		
		return camposExtrass;
	}
}
