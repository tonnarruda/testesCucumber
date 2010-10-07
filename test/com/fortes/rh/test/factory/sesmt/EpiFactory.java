package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Epi;

public class EpiFactory
{
	public static Epi getEntity()
	{
		Epi epi = new Epi();
		epi.setNome("nome");
		epi.setFabricante("fabricante");
		epi.setFardamento(true);
		epi.setEmpresa(null);
		return epi;
	}

	public static Epi getEntity(Long id)
	{
		Epi epi = getEntity();
		epi.setId(id);
		return epi;
	}
}