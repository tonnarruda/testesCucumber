package com.fortes.rh.web.dwr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.geral.UsuarioMensagem;

@Component
public class UsuarioMensagemDWR
{
	@Autowired
	private UsuarioMensagemManager usuarioMensagemManager;

	public void gravarMensagemLida(Long usuarioMensagemId, Long empresaId, boolean lida)
	{
		UsuarioMensagem usuarioMensagem = usuarioMensagemManager.findByIdProjection(usuarioMensagemId, empresaId);
		usuarioMensagem.setLida(lida);
		usuarioMensagemManager.update(usuarioMensagem);
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager)
	{
		this.usuarioMensagemManager = usuarioMensagemManager;
	}
}
