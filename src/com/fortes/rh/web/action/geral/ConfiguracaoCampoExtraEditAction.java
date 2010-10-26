package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ConfiguracaoCampoExtraEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras;
	private int[] ordens = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,16};
	private boolean habilitaCampoExtra;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
		
	public String prepareUpdate() throws Exception
	{
		habilitaCampoExtra = parametrosDoSistemaManager.findByIdProjection(1L).isCampoExtraColaborador();
		configuracaoCampoExtras = configuracaoCampoExtraManager.findAll(new String[]{"posicao"});
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{		
		for (ConfiguracaoCampoExtra campoExtra : configuracaoCampoExtras)
			configuracaoCampoExtraManager.update(campoExtra);

		parametrosDoSistemaManager.updateCampoExtra(habilitaCampoExtra);
		
		addActionMessage("Configurações gravadas com sucesso!");
		
		return Action.SUCCESS;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager)
	{
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}
	
	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras()
	{
		return configuracaoCampoExtras;
	}

	public void setConfiguracaoCampoExtras(Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras) 
	{
		this.configuracaoCampoExtras = configuracaoCampoExtras;
	}

	public int[] getOrdens() {
		return ordens;
	}

	public void setOrdens(int[] ordens) {
		this.ordens = ordens;
	}

	public void setParametrosDoSistemaManager(
			ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra) {
		this.habilitaCampoExtra = habilitaCampoExtra;
	}
}
