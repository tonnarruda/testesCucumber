package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Epc;

public class EpcFactory
{
	public static Epc getEntity()
	{
		Epc epc = new Epc();
		epc.setDescricao("descricao");
		epc.setEmpresa(null);
		return epc;
	}

	public static Epc getEntity(Long id)
	{
		Epc epc = getEntity();
		epc.setId(id);
		return epc;
	}
}