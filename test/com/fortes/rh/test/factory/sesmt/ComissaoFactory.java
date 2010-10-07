package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.Comissao;

public class ComissaoFactory
{
	public static Comissao getEntity()
	{
		Comissao comissao = new Comissao();

		return comissao;
	}

	public static Comissao getEntity(Long id)
	{
		Comissao comissao = getEntity();
		comissao.setId(id);

		return comissao;
	}
}