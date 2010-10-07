package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ComissaoReuniao;

public class ComissaoReuniaoFactory
{
	public static ComissaoReuniao getEntity()
	{
		return new ComissaoReuniao();
	}

	public static ComissaoReuniao getEntity(Long id)
	{
		ComissaoReuniao comissaoReuniao = getEntity();
		comissaoReuniao.setId(id);
		return comissaoReuniao;
	}
}
