package com.fortes.rh.business.geral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.UsuarioNoticiaDao;
import com.fortes.rh.model.geral.UsuarioNoticia;

@Component
public class UsuarioNoticiaManagerImpl extends GenericManagerImpl<UsuarioNoticia, UsuarioNoticiaDao> implements UsuarioNoticiaManager
{
	@Autowired
	UsuarioNoticiaManagerImpl(UsuarioNoticiaDao dao) {
		setDao(dao);
	}
	
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