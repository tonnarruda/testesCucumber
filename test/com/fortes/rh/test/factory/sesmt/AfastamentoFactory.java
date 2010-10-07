package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Afastamento;

public class AfastamentoFactory
{
	public static Afastamento getEntity()
	{
		Afastamento afastamento = new Afastamento();

		return afastamento;
	}

	public static Afastamento getEntity(Long id)
	{
		Afastamento afastamento = getEntity();
		afastamento.setId(id);

		return afastamento;
	}
}