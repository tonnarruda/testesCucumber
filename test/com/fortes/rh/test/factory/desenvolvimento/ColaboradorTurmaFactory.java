package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;

public class ColaboradorTurmaFactory
{
	public static ColaboradorTurma getEntity()
	{
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
		return colaboradorTurma;
	}

	public static ColaboradorTurma getEntity(long id)
	{
		ColaboradorTurma colaboradorTurma = getEntity();
		colaboradorTurma.setId(id);
		return colaboradorTurma;
	}
}
