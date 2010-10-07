package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.MedicaoRisco;

public class MedicaoRiscoFactory
{
	public static MedicaoRisco getEntity()
	{
		MedicaoRisco medicaoRisco = new MedicaoRisco();
		medicaoRisco.setId(null);
		return medicaoRisco;
	}

	public static MedicaoRisco getEntity(Long id)
	{
		MedicaoRisco medicaoRisco = getEntity();
		medicaoRisco.setId(id);

		return medicaoRisco;
	}

	public static Collection<MedicaoRisco> getCollection()
	{
		Collection<MedicaoRisco> medicaoRiscos = new ArrayList<MedicaoRisco>();
		medicaoRiscos.add(getEntity());

		return medicaoRiscos;
	}
	
	public static Collection<MedicaoRisco> getCollection(Long id)
	{
		Collection<MedicaoRisco> medicaoRiscos = new ArrayList<MedicaoRisco>();
		medicaoRiscos.add(getEntity(id));
		
		return medicaoRiscos;
	}
}
