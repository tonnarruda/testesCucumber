package com.fortes.rh.web.action.geral;


import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ConfiguracaoRelatorioDinamicoManager;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ConfiguracaoRelatorioDinamicoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ConfiguracaoRelatorioDinamicoManager configuracaoRelatorioDinamicoManager;

	private String campos;
	private String titulo;
	
	public String update() throws Exception
	{
		try {
			configuracaoRelatorioDinamicoManager.update(campos, titulo, SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId());
			return Action.SUCCESS;			
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;			
		}
	}

	public String getCampos() {
		return campos;
	}

	public void setCampos(String campos) {
		this.campos = campos;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}