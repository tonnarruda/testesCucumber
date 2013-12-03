package com.fortes.rh.test.factory.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;

public class MedidaRiscoFasePcmatFactory
{
	public static MedidaRiscoFasePcmat getEntity()
	{
		MedidaRiscoFasePcmat medidaRiscoFasePcmat = new MedidaRiscoFasePcmat();
		medidaRiscoFasePcmat.setId(null);
		return medidaRiscoFasePcmat;
	}

	public static MedidaRiscoFasePcmat getEntity(Long id)
	{
		MedidaRiscoFasePcmat medidaRiscoFasePcmat = getEntity();
		medidaRiscoFasePcmat.setId(id);

		return medidaRiscoFasePcmat;
	}

	public static Collection<MedidaRiscoFasePcmat> getCollection()
	{
		Collection<MedidaRiscoFasePcmat> medidaRiscoFasePcmats = new ArrayList<MedidaRiscoFasePcmat>();
		medidaRiscoFasePcmats.add(getEntity());

		return medidaRiscoFasePcmats;
	}
	
	public static Collection<MedidaRiscoFasePcmat> getCollection(Long id)
	{
		Collection<MedidaRiscoFasePcmat> medidaRiscoFasePcmats = new ArrayList<MedidaRiscoFasePcmat>();
		medidaRiscoFasePcmats.add(getEntity(id));
		
		return medidaRiscoFasePcmats;
	}
}
