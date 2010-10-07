package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Bairro;

public class BairroFactory
{
	public static Bairro getEntity()
	{
		Bairro estado = new Bairro();
		estado.setId(null);

		return estado;
	}

	public static Bairro getEntity(Long id)
	{
		Bairro estado = getEntity();
		estado.setId(id);
		
		return estado;
	}
}
