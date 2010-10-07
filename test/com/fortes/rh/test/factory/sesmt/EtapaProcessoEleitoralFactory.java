package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.EtapaProcessoEleitoral;

public class EtapaProcessoEleitoralFactory
{
	public static EtapaProcessoEleitoral getEntity()
	{
		EtapaProcessoEleitoral etapaProcessoEleitoral = new EtapaProcessoEleitoral();
		etapaProcessoEleitoral.setId(null);
		etapaProcessoEleitoral.setNome("Etapa ");
		return etapaProcessoEleitoral;
	}

	public static EtapaProcessoEleitoral getEntity(Long id)
	{
		EtapaProcessoEleitoral etapaProcessoEleitoral = getEntity();
		etapaProcessoEleitoral.setId(id);
		return etapaProcessoEleitoral;
	}

}
