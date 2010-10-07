package com.fortes.rh.web.action;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

public abstract class MyActionSupport extends ActionSupport
{
	private Empresa empresaSistema = null;
	private String actionErr = null;
	private String actionMsg = null;
	public static final String MESSAGE = "message";

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
}