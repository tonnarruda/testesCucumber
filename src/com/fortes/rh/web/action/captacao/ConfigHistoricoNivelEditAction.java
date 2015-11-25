package com.fortes.rh.web.action.captacao;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.captacao.ConfigHistoricoNivelManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaFaixaSalarialManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.NivelCompetenciaHistoricoManager;
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
	private NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager;
	private CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager;
	private ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager;
	
	private boolean podeEditar;
	private boolean obrigarPercentual;

	public String prepareInsert() throws Exception
	{
		configHistoricoNivels = configHistoricoNivelManager.findNiveisCompetenciaByEmpresa(getEmpresaSistema().getId());
		obrigarPercentual = criterioAvaliacaoCompetenciaManager.existeCriterioAvaliacaoCompetencia(getEmpresaSistema().getId());
		podeEditar = true;
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if(nivelCompetenciaHistorico != null){
			nivelCompetenciaHistorico = nivelCompetenciaHistoricoManager.findById(nivelCompetenciaHistorico.getId());
			configHistoricoNivels = configHistoricoNivelManager.findByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico.getId());
			
			podeEditar = !configuracaoNivelCompetenciaFaixaSalarialManager.existByNivelCompetenciaHistoricoId(nivelCompetenciaHistorico.getId());
			obrigarPercentual = criterioAvaliacaoCompetenciaManager.existeCriterioAvaliacaoCompetencia(getEmpresaSistema().getId());
		}
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		nivelCompetenciaHistorico = new NivelCompetenciaHistorico();
		nivelCompetenciaHistorico.setData(new Date());
		nivelCompetenciaHistorico.setEmpresa(getEmpresaSistema());
		nivelCompetenciaHistorico.setConfigHistoricoNiveis(montaConfigHistoricoNivel());
		
		try {
			nivelCompetenciaHistoricoManager.save(nivelCompetenciaHistorico);
			setActionMsg("Histórico de níveis de competência salvo com sucesso.");
		}
		catch (DataIntegrityViolationException e)
		{
			addActionWarning("Já existe uma histórico de níveis de competência cadastrado nesta data ( " + nivelCompetenciaHistorico.getDataFormatada() + " ). ");
			e.printStackTrace();
			nivelCompetenciaHistorico = null;
			prepareInsert();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError(e.getMessage());
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			nivelCompetenciaHistorico.setConfigHistoricoNiveis(montaConfigHistoricoNivel());
			nivelCompetenciaHistoricoManager.updateNivelConfiguracaoHistorico(nivelCompetenciaHistorico);
			setActionMsg("Histórico de níveis de competência atualizado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Ocorreu um erro ao atualizar o histórico de níveis de competência.");
			prepareUpdate();
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}
	
	private Collection<ConfigHistoricoNivel> montaConfigHistoricoNivel()
	{
		Collection<ConfigHistoricoNivel> configHistoricoNiveisTemp = new ArrayList<ConfigHistoricoNivel>();
		
		for (ConfigHistoricoNivel configHistoricoNivel : configHistoricoNivels) 
			if(configHistoricoNivel.getOrdem()!= null || configHistoricoNivel.getPercentual()!= null){
				configHistoricoNivel.setNivelCompetenciaHistorico(nivelCompetenciaHistorico);
				configHistoricoNiveisTemp.add(configHistoricoNivel);
			}
		return configHistoricoNiveisTemp;
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

	public void setConfigHistoricoNivels(Collection<ConfigHistoricoNivel> configHistoricoNivels) {
		this.configHistoricoNivels = configHistoricoNivels;
	}

	public void setNivelCompetenciaHistoricoManager(NivelCompetenciaHistoricoManager nivelCompetenciaHistoricoManager) {
		this.nivelCompetenciaHistoricoManager = nivelCompetenciaHistoricoManager;
	}

	public void setCriterioAvaliacaoCompetenciaManager(
			CriterioAvaliacaoCompetenciaManager criterioAvaliacaoCompetenciaManager) {
		this.criterioAvaliacaoCompetenciaManager = criterioAvaliacaoCompetenciaManager;
	}

	public boolean isPodeEditar() {
		return podeEditar;
	}

	public boolean isObrigarPercentual() {
		return obrigarPercentual;
	}

	public void setConfiguracaoNivelCompetenciaFaixaSalarialManager(
			ConfiguracaoNivelCompetenciaFaixaSalarialManager configuracaoNivelCompetenciaFaixaSalarialManager) {
		this.configuracaoNivelCompetenciaFaixaSalarialManager = configuracaoNivelCompetenciaFaixaSalarialManager;
	}
}
