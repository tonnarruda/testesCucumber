package com.fortes.rh.web.action.geral;

import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;

@SuppressWarnings("serial")
public class UsuarioMensagemListAction extends ActionSupport
{
	private UsuarioMensagemManager usuarioMensagemManager = null;

	private UsuarioMensagem usuarioMensagem;

	private boolean fromPopup = false; // indica quando o delete se origina do
										// popup

	private Long usuarioMensagemProximoId;

	private Long usuarioMensagemAnteriorId;

	private Long[] usuarioMensagemIds;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		usuarioMensagemManager.delete(usuarioMensagem, usuarioMensagemIds);
			
		if(!fromPopup)
			return Action.SUCCESS;
		else
			return "success_popup";
	}

	public UsuarioMensagem getUsuarioMensagem()
	{
		if(usuarioMensagem == null)
		{
			usuarioMensagem = new UsuarioMensagem();
		}
		return usuarioMensagem;
	}

	public void setUsuarioMensagem(UsuarioMensagem usuarioMensagem)
	{
		this.usuarioMensagem = usuarioMensagem;
	}

	public void setUsuarioMensagemManager(UsuarioMensagemManager usuarioMensagemManager)
	{
		this.usuarioMensagemManager = usuarioMensagemManager;
	}

	public boolean isFromPopup()
	{
		return fromPopup;
	}

	public void setFromPopup(boolean fromPopup)
	{
		this.fromPopup = fromPopup;
	}

	public Long getUsuarioMensagemAnteriorId()
	{
		return usuarioMensagemAnteriorId;
	}

	public void setUsuarioMensagemAnteriorId(Long usuarioMensagemAnteriorId)
	{
		this.usuarioMensagemAnteriorId = usuarioMensagemAnteriorId;
	}

	public Long getUsuarioMensagemProximoId()
	{
		return usuarioMensagemProximoId;
	}

	public void setUsuarioMensagemProximoId(Long usuarioMensagemProximoId)
	{
		this.usuarioMensagemProximoId = usuarioMensagemProximoId;
	}

	public void setUsuarioMensagemIds(Long[] usuarioMensagemIds)
	{
		this.usuarioMensagemIds = usuarioMensagemIds;
	}
}