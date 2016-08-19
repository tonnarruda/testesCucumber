package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.UsuarioNoticiaManager;


public class UsuarioNoticiaDWR
{
	private UsuarioNoticiaManager usuarioNoticiaManager;

	public void marcarLida(Long usuarioId, Long noticiaId) 
	{
		usuarioNoticiaManager.marcarLida(usuarioId, noticiaId);
	}

	public void setUsuarioNoticiaManager(UsuarioNoticiaManager usuarioNoticiaManager)
	{
		this.usuarioNoticiaManager = usuarioNoticiaManager;
	}

}
