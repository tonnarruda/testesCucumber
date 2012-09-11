package com.fortes.rh.web.dwr;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.util.StringUtil;

public class UsuarioDWR
{
	private UsuarioManager usuarioManager;
	
	public void gravarLayoutCaixasMensagens(Long usuarioId, Character[] ordem, Character[] minimizadas) throws Exception 
	{
		try {
			usuarioManager.updateConfiguracoesMensagens(usuarioId, StringUtil.converteCharArrayToString(ordem), StringUtil.converteCharArrayToString(minimizadas));

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ocorreu um erro ao gravar as configurações da página inicial");
		}
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) 
	{
		this.usuarioManager = usuarioManager;
	}
}
