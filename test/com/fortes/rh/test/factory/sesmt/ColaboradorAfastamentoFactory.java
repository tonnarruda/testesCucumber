package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ColaboradorAfastamento;

public class ColaboradorAfastamentoFactory
{
	public static ColaboradorAfastamento getEntity()
	{
		ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
		return colaboradorAfastamento;
	}

	public static ColaboradorAfastamento getEntity(Long id)
	{
		ColaboradorAfastamento colaboradorAfastamento = getEntity();
		colaboradorAfastamento.setId(id);
		return colaboradorAfastamento;
	}
}