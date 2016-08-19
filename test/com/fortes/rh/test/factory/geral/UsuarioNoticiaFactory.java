package com.fortes.rh.test.factory.geral;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Noticia;
import com.fortes.rh.model.geral.UsuarioNoticia;

public class UsuarioNoticiaFactory
{
	public static UsuarioNoticia getEntity()
	{
		UsuarioNoticia usuarioNoticia = new UsuarioNoticia();
		usuarioNoticia.setId(null);

		return usuarioNoticia;
	}
	
	public static UsuarioNoticia getEntity(Long id, Usuario usuario, Noticia noticia)
	{
		UsuarioNoticia usuarioNoticia = new UsuarioNoticia();
		usuarioNoticia.setId(id);
		usuarioNoticia.setUsuario(usuario);
		usuarioNoticia.setNoticia(noticia);
		
		return usuarioNoticia;
	}

}
