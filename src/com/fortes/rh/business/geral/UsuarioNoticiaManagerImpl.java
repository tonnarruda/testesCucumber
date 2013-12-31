package com.fortes.rh.business.geral;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.UsuarioNoticiaDao;
import com.fortes.rh.model.geral.UsuarioNoticia;

public class UsuarioNoticiaManagerImpl extends GenericManagerImpl<UsuarioNoticia, UsuarioNoticiaDao> implements UsuarioNoticiaManager
{
	public void marcarLida(Long usuarioId, Long noticiaId) 
	{
		if (!this.verifyExists(new String[] { "usuario.id", "noticia.id" }, new Object[] { usuarioId, noticiaId }))
		{
			UsuarioNoticia usuarioNoticia = new UsuarioNoticia();
			usuarioNoticia.setUsuarioId(usuarioId);
			usuarioNoticia.setNoticiaId(noticiaId);
			
			this.save(usuarioNoticia);
		}
	}
}