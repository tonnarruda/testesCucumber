package com.fortes.rh.test.factory.captacao;

import com.fortes.rh.model.captacao.EtapaSeletiva;

public class EtapaSeletivaFactory
{
	public static EtapaSeletiva getEntity()
	{
		EtapaSeletiva etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(null);
		etapaSeletiva.setNome("nome");
		etapaSeletiva.setOrdem(1);
		return etapaSeletiva;
	}
}
