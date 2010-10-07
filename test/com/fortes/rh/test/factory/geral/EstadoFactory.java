package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.Estado;

public class EstadoFactory
{
	public static Estado getEntity()
	{
		Estado estado = new Estado();
		estado.setId(null);
		estado.setNome("Ceará");
		estado.setSigla("CE");

		return estado;
	}

	public static Estado getEntity(Long id)
	{
		Estado estado = getEntity();
		estado.setId(id);
		
		return estado;
	}
}
