package com.fortes.rh.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.StringUtil;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

public abstract class MyActionSupport extends ActionSupport
{
	private Empresa empresaSistema = null;
	private Usuario usuarioSistema = null;
	private Long videoAjuda = null;
	private String actionErr = null;
	private String actionMsg = null;
	public static final String MESSAGE = "message";
	
	private List<String> actionWarnings = new ArrayList<String>();
	private List<String> actionSuccess = new ArrayList<String>();

	public Empresa getEmpresaSistema()
	{
		if (empresaSistema == null)
			empresaSistema = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession());
		return empresaSistema;
	}
	
	public void setEmpresaSistema(Empresa empresaSistema)
	{
		this.empresaSistema = empresaSistema;
	}
	
	public Usuario getUsuarioLogado()
	{
		if (usuarioSistema == null)
			usuarioSistema = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		return usuarioSistema;
	}

	public void setUsuarioLogado(Usuario usuario)
	{
		this.usuarioSistema = usuario;
	}

	public String getActionMsg()
	{
		return actionMsg;
	}

	public void setActionMsg(String actionMsg)
	{
		this.actionMsg = actionMsg;

		if (actionMsg != null && !actionMsg.equals(""))
		{
			//TODO actionmessage duplicada, Francisco e Arnaldo 21/01/09. Duvidas ligue 190.  
			clearErrorsAndMessages();
			addActionMessage(this.actionMsg);
		}
	}

	public String getActionErr()
	{
		return actionErr;
	}

	public void setActionErr(String actionErr)
	{
		this.actionErr = actionErr;
	}
	
	public String updateFilter()
	{
		return Action.NONE;
	}
	
	public String getCalculoHash()
	{
		String data = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		Integer calculoHash  = (Integer.parseInt(data) * 2) / 64;
		
		return StringUtil.encodeString(calculoHash.toString());
	}

	public Long getVideoAjuda() 
	{
		return videoAjuda;
	}

	public void setVideoAjuda(Long videoAjuda) 
	{
		this.videoAjuda = videoAjuda;
	}

	public List<String> getActionWarnings() 
	{
		return actionWarnings;
	}

	public void setActionWarnings(List<String> actionWarnings) 
	{
		this.actionWarnings = actionWarnings;
	}
	
	public void addActionWarning(String actionWarning) 
	{
		this.actionWarnings.add(actionWarning);
	}
	
	public List<String> getActionSuccess() 
	{
		return actionSuccess;
	}

	public void setActionSuccess(List<String> actionSuccess) 
	{
		this.actionSuccess = actionSuccess;
	}
	
	public void addActionSuccess(String actionSuccess) 
	{
		this.actionSuccess.add(actionSuccess);
	}
}