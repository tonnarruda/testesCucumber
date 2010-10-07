package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class ParametrosDoSistemaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PerfilManager perfilManager;
	private ExameManager exameManager;

	private ParametrosDoSistema parametrosDoSistema;

	private Collection<Perfil> perfils;
	private Collection<Exame> exames;

	public String prepareUpdate() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		perfils = perfilManager.findAll();
		exames = exameManager.findAllSelect(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaAux = parametrosDoSistemaManager.findById(parametrosDoSistema.getId());

		if(StringUtils.isBlank(parametrosDoSistema.getEmailPass()))
			parametrosDoSistema.setEmailPass(parametrosDoSistemaAux.getEmailPass());

		if (parametrosDoSistemaAux.getModulos() != null)
			parametrosDoSistema.setModulos(parametrosDoSistemaAux.getModulos());
		
		//evitando problema de vir instância sem o id (TransientObjectException) 
		if (parametrosDoSistema.getExame() != null && parametrosDoSistema.getExame().getId() == null)
			parametrosDoSistema.setExame(null);

		parametrosDoSistemaManager.update(parametrosDoSistema);
		
		perfils = perfilManager.findAll();
		exames = exameManager.findAllSelect(getEmpresaSistema().getId());
		addActionMessage("Configurações do Sistema atualizadas com sucesso!");

		return Action.SUCCESS;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		if(parametrosDoSistema == null)
			parametrosDoSistema = new ParametrosDoSistema();
		return parametrosDoSistema;
	}

	public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema)
	{
		this.parametrosDoSistema = parametrosDoSistema;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager)
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public void setPerfilManager(PerfilManager perfilManager)
	{
		this.perfilManager = perfilManager;
	}

	public Collection<Perfil> getPerfils()
	{
		return perfils;
	}

	public void setPerfils(Collection<Perfil> perfils)
	{
		this.perfils = perfils;
	}

	public void setExameManager(ExameManager exameManager) {
		this.exameManager = exameManager;
	}

	public Collection<Exame> getExames() {
		return exames;
	}
}