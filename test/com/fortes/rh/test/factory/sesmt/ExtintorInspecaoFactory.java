package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ExtintorInspecao;

public class ExtintorInspecaoFactory
{
	public static ExtintorInspecao getEntity()
	{
		ExtintorInspecao extintorInspecao = new ExtintorInspecao();

		return extintorInspecao;
	}

	public static ExtintorInspecao getEntity(Long id)
	{
		ExtintorInspecao extintorInspecao = getEntity();

		extintorInspecao.setId(id);

		return extintorInspecao;
	}
}