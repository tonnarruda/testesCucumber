package com.fortes.rh.test.factory.acesso;

import com.fortes.rh.model.acesso.Papel;

public class PapelFactory
{
	public static Papel getEntity()
	{
		Papel papel = new Papel();

		papel.setId(null);
		papel.setCodigo("ROLE_I");

		return papel;
	}

	public static Papel getEntity(Long id)
	{
		Papel papel = getEntity();
		papel.setId(id);
		
		return papel;
	}
	
	public static Papel getEntity(Long id, String codigo)
	{
		Papel usuario = getEntity(id);
		usuario.setCodigo(codigo);

		return usuario;
	}
}
