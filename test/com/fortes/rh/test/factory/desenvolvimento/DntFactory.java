package com.fortes.rh.test.factory.desenvolvimento;

import java.util.Date;

import com.fortes.rh.model.desenvolvimento.DNT;

public class DntFactory
{
	public static DNT getEntity()
	{
		DNT dnt = new DNT();
		dnt.setId(null);
		dnt.setNome("Teste");
		dnt.setData(new Date());
		dnt.setEmpresa(null);
		return dnt;
	}

	public static DNT getEntity(long id)
	{
		DNT dnt = getEntity();
		dnt.setId(id);
		return dnt;
	}
}
