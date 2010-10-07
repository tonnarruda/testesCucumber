package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.geral.UsuarioMensagem;

public class UsuarioMensagemFactory
{

	public static UsuarioMensagem getEntity()
	{
		UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
		return usuarioMensagem;
	}

	public static UsuarioMensagem getEntity(Long id)
	{
		UsuarioMensagem usuarioMensagem = getEntity();
		usuarioMensagem.setId(id);

		return usuarioMensagem;
	}

}
