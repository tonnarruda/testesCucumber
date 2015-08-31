package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
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
	
	public static UsuarioMensagem getEntity(Usuario usuario, Mensagem mensagem, Empresa empresa, boolean lida)
	{
		UsuarioMensagem usuarioMensagem = getEntity();
		usuarioMensagem.setUsuario(usuario);
		usuarioMensagem.setMensagem(mensagem);
		usuarioMensagem.setEmpresa(empresa);
		usuarioMensagem.setLida(lida);
		
		return usuarioMensagem;
	}

}
