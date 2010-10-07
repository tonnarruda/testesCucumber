package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;

public class ColaboradorPresencaFactory
{
	public static ColaboradorPresenca getEntity()
	{
		ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca();
		return colaboradorPresenca;
	}

	public static ColaboradorPresenca getEntity(long id)
	{
		ColaboradorPresenca colaboradorPresenca = getEntity();
		colaboradorPresenca.setId(id);
		return colaboradorPresenca;
	}
}
