package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.MotivoDemissao;

public class MotivoDemissaoFactory
{
	public static MotivoDemissao getEntity()
	{
		MotivoDemissao motivoDemissao = new MotivoDemissao();
		motivoDemissao.setMotivo("Faltas");
		return motivoDemissao;
	}

	public static MotivoDemissao getEntity(Long id)
	{
		MotivoDemissao motivoDemissao = getEntity();
		motivoDemissao.setId(id);
		return motivoDemissao;
	}
}
