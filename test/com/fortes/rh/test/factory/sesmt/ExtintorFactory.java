package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Extintor;

public class ExtintorFactory
{
	public static Extintor getEntity()
	{
		Extintor extintor = new Extintor();

		return extintor;
	}

	public static Extintor getEntity(Long id)
	{
		Extintor extintor = getEntity();

		extintor.setId(id);

		return extintor;
	}
}