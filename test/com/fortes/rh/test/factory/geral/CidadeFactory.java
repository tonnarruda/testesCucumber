package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Cidade;

public class CidadeFactory
{
	public static Cidade getEntity()
	{
		Cidade cidade = new Cidade();
		cidade.setId(null);
		cidade.setNome("cidade");
		cidade.setUf(null);
		cidade.setCodigoAC("0001");

		return cidade;
	}

	public static Cidade getEntity(long id)
	{
		Cidade cidade = getEntity();
		cidade.setId(id);
		
		return cidade;
	}
}
