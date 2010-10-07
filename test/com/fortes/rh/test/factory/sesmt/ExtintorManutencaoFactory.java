package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ExtintorManutencao;

public class ExtintorManutencaoFactory
{
	public static ExtintorManutencao getEntity()
	{
		ExtintorManutencao extintorManutencao = new ExtintorManutencao();

		return extintorManutencao;
	}

	public static ExtintorManutencao getEntity(Long id)
	{
		ExtintorManutencao extintorManutencao = getEntity();

		extintorManutencao.setId(id);

		return extintorManutencao;
	}
}