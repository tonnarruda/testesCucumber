package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;

public class RiscoMedicaoRiscoFactory
{
	public static RiscoMedicaoRisco getEntity()
	{
		RiscoMedicaoRisco riscoMedicaoRisco = new RiscoMedicaoRisco();
		riscoMedicaoRisco.setId(null);
		return riscoMedicaoRisco;
	}

	public static RiscoMedicaoRisco getEntity(Long id)
	{
		RiscoMedicaoRisco riscoMedicaoRisco = getEntity();
		riscoMedicaoRisco.setId(id);

		return riscoMedicaoRisco;
	}

	public static Collection<RiscoMedicaoRisco> getCollection()
	{
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(getEntity());

		return riscoMedicaoRiscos;
	}
	
	public static Collection<RiscoMedicaoRisco> getCollection(Long id)
	{
		Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
		riscoMedicaoRiscos.add(getEntity(id));
		
		return riscoMedicaoRiscos;
	}
}
