package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ComissaoPeriodo;

public class ComissaoPeriodoFactory
{
	public static ComissaoPeriodo getEntity()
	{
		ComissaoPeriodo comissaoPeriodo = new ComissaoPeriodo();
		return comissaoPeriodo;
	}

	public static ComissaoPeriodo getEntity(Long id)
	{
		ComissaoPeriodo comissaoPeriodo = new ComissaoPeriodo();
		comissaoPeriodo.setId(id);
		return comissaoPeriodo;
	}
}
