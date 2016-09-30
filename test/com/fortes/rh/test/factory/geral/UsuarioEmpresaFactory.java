package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.geral.Empresa;

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
	
	public static UsuarioEmpresa getEntity(Long id, Usuario usuario, Empresa empresa, Perfil perfil)
	{
		UsuarioEmpresa usuarioEmpresa = getEntity(id);
		usuarioEmpresa.setUsuario(usuario);
		usuarioEmpresa.setEmpresa(empresa);
		usuarioEmpresa.setPerfil(perfil);
		
		return usuarioEmpresa;
	}
	
	public static UsuarioEmpresa getEntity(Long id, Usuario usuario)
	{
		UsuarioEmpresa usuarioEmpresa = getEntity(id);
		usuarioEmpresa.setUsuario(usuario);
		
		return usuarioEmpresa;
	}


}
