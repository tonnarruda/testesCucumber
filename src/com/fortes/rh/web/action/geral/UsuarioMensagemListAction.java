package com.fortes.rh.web.action.geral;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.UsuarioMensagemManager;
import com.fortes.rh.model.geral.UsuarioMensagem;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class UsuarioMensagemListAction extends MyActionSupport
{
	@Autowired private UsuarioMensagemManager usuarioMensagemManager;
	@Autowired private UsuarioMensagem usuarioMensagem;

	private boolean fromPopup = false;
	private boolean fromTodasMensagens = false;

	private Long usuarioMensagemProximoId;
	private Long usuarioMensagemAnteriorId;

	private Long[] usuarioMensagemIds;
	
	private char tipo;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		usuarioMensagemManager.delete(usuarioMensagem, usuarioMensagemIds);
			
		if (fromPopup)
			return "success_popup";
		
		else if (fromTodasMensagens)
			return "success_mensagens";
		
		else
			return Action.SUCCESS;
	}

	public UsuarioMensagem getUsuarioMensagem()
	{
		if(usuarioMensagem == null)
		{
			usuarioMensagem = new UsuarioMensagem();
		}
		return usuarioMensagem;
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

	public boolean isFromTodasMensagens() 
	{
		return fromTodasMensagens;
	}

	public void setFromTodasMensagens(boolean fromTodasMensagens) 
	{
		this.fromTodasMensagens = fromTodasMensagens;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}