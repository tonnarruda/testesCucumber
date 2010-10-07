package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Exame;

public class ExameFactory
{

	public static Exame getEntity(){

		Exame exame = new Exame();
		exame.setNome("nome");
		exame.setPeriodicidade(1);
		exame.setEmpresa(null);

		return exame;
	}

	public static Exame getEntity(Long id)
	{
		Exame exame = getEntity();
		exame.setId(id);
		return exame;
	}
}