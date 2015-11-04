package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ConfigHistoricoNivelEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ConfigHistoricoNivelManager configHistoricoNivelManager;
	private ConfigHistoricoNivel configHistoricoNivel;
	private Collection<ConfigHistoricoNivel> configHistoricoNivels;
	private NivelCompetenciaHistorico nivelCompetenciaHistorico;


	public String list() throws Exception
	{
		configHistoricoNivels = configHistoricoNivelManager.findByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico.getId());
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(configHistoricoNivel != null && configHistoricoNivel.getId() != null)
			configHistoricoNivel = (ConfigHistoricoNivel) configHistoricoNivelManager.findById(configHistoricoNivel.getId());

	}

	public String prepareInsert() throws Exception
	{
		configHistoricoNivels = configHistoricoNivelManager.findByEmpresaId(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		configHistoricoNivelManager.save(configHistoricoNivel);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		configHistoricoNivelManager.update(configHistoricoNivel);
		return Action.SUCCESS;
	}
	
	public ConfigHistoricoNivel getConfigHistoricoNivel()
	{
		if(configHistoricoNivel == null)
			configHistoricoNivel = new ConfigHistoricoNivel();
		return configHistoricoNivel;
	}

	public void setConfigHistoricoNivel(ConfigHistoricoNivel configHistoricoNivel)
	{
		this.configHistoricoNivel = configHistoricoNivel;
	}

	public void setConfigHistoricoNivelManager(ConfigHistoricoNivelManager configHistoricoNivelManager)
	{
		this.configHistoricoNivelManager = configHistoricoNivelManager;
	}
	
	public Collection<ConfigHistoricoNivel> getConfigHistoricoNivels()
	{
		return configHistoricoNivels;
	}

	public NivelCompetenciaHistorico getNivelCompetenciaHistorico() {
		return nivelCompetenciaHistorico;
	}

	public void setNivelCompetenciaHistorico(NivelCompetenciaHistorico nivelCompetenciaHistorico) {
		this.nivelCompetenciaHistorico = nivelCompetenciaHistorico;
	}
}
