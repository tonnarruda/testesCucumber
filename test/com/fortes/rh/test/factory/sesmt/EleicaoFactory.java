package com.fortes.rh.test.factory.sesmt;

import java.util.Date;

import com.fortes.rh.model.sesmt.Eleicao;

public class EleicaoFactory
{
	public static Eleicao getEntity()
	{
		Eleicao eleicao = new Eleicao();

		eleicao.setPosse(new Date());

		return eleicao;
	}

	public static Eleicao getEntity(Long id)
	{
		Eleicao eleicao = getEntity();
		eleicao.setId(id);
		
		return eleicao;
	}
}
