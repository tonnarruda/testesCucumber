package com.fortes.rh.test.factory.desenvolvimento;

import com.fortes.rh.model.desenvolvimento.DiaTurma;

public class DiaTurmaFactory
{
	public static DiaTurma getEntity()
	{
		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(null);
		return diaTurma;
	}

	public static DiaTurma getEntity(long id)
	{
		DiaTurma diaTurma = getEntity();
		diaTurma.setId(id);
		return diaTurma;
	}
}
