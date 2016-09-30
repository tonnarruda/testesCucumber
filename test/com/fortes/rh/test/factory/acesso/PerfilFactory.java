package com.fortes.rh.test.factory.acesso;

import com.fortes.rh.model.acesso.Perfil;

public class PerfilFactory
{
	public static Perfil getEntity()
	{
		Perfil perfil = new Perfil();
		perfil.setId(null);

		return perfil;
	}

	public static Perfil getEntity(Long id)
	{
		Perfil perfil = getEntity();
		perfil.setId(id);
		
		return perfil;
	}
	
	public static Perfil getEntity(Long id, String nome)
	{
		Perfil perfil = getEntity(id);
		perfil.setNome(nome);
		return perfil;
	}
}
