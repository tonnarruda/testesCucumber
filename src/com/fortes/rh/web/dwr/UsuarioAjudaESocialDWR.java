package com.fortes.rh.web.dwr;

import com.fortes.rh.business.geral.UsuarioAjudaESocialManager;

public class UsuarioAjudaESocialDWR
{
	private UsuarioAjudaESocialManager usuarioAjudaESocialManager;

	public void saveUsuarioAjuda(Long usuarioId, String localAjuda)
	{
		try {
			usuarioAjudaESocialManager.saveUsuarioAjuda(usuarioId, localAjuda);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUsuarioAjudaESocialManager(UsuarioAjudaESocialManager usuarioAjudaESocialManager) {
		this.usuarioAjudaESocialManager = usuarioAjudaESocialManager;
	}
}
