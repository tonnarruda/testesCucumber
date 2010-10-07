package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.acesso.UsuarioEmpresa;

public class UsuarioEmpresaFactory
{

	public static UsuarioEmpresa getEntity()
	{
		UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
		return usuarioEmpresa;
	}

	public static UsuarioEmpresa getEntity(Long id)
	{
		UsuarioEmpresa usuarioEmpresa = getEntity();
		usuarioEmpresa.setId(id);

		return usuarioEmpresa;
	}

}
