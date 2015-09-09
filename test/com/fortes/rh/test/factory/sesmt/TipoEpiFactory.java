package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.TipoEPI;

public class TipoEpiFactory
{
	public static TipoEPI getEntity()
	{
		TipoEPI tipoEPI = new TipoEPI();
		tipoEPI.setNome("descrição tipo epi");
		return tipoEPI;
	}

	public static TipoEPI getEntity(Long id)
	{
		TipoEPI tipoEPI = getEntity();
		tipoEPI.setId(id);
		return tipoEPI;
	}
}