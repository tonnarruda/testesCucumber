package com.fortes.rh.test.factory.sesmt;

import com.fortes.rh.model.sesmt.ComissaoEleicao;

public class ComissaoEleicaoFactory
{
	public static ComissaoEleicao getEntity()
	{
		ComissaoEleicao comissaoComissaoEleicao = new ComissaoEleicao();

		return comissaoComissaoEleicao;
	}

	public static ComissaoEleicao getEntity(Long id)
	{
		ComissaoEleicao comissaoComissaoEleicao = getEntity();
		comissaoComissaoEleicao.setId(id);
		
		return comissaoComissaoEleicao;
	}
}