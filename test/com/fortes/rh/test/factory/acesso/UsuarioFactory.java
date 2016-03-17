package com.fortes.rh.test.factory.acesso;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.util.StringUtil;

public class UsuarioFactory
{
	public static Usuario getEntity()
	{
		Usuario usuario = new Usuario();

		usuario.setId(null);
		usuario.setNome("nome do usuario");
		usuario.setAcessoSistema(true);
		usuario.setLogin(StringUtil.getSenhaRandom(10));
		usuario.setSenha("senha");

		return usuario;
	}

	public static Usuario getEntity(long id)
	{
		Usuario usuario = getEntity();
		usuario.setId(id);
		
		return usuario;
	}
	
	public static Usuario getEntity(String login, Boolean acessoSistema)
	{
		Usuario usuario = getEntity();
		usuario.setAcessoSistema(acessoSistema);
		usuario.setLogin(login);

		return usuario;
	}
}
