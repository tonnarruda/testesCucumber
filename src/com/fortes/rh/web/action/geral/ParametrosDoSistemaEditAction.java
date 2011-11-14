package com.fortes.rh.web.action.geral;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.acesso.PerfilManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Perfil;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class ParametrosDoSistemaEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private PerfilManager perfilManager;

	private ParametrosDoSistema parametrosDoSistema;

	private Collection<Perfil> perfils;

	private String[] camposCandidatoObrigatorios;
	private String[] camposCandidatoVisivels;
	
	private boolean habilitaCampoExtra;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	
	public String prepareUpdate() throws Exception
	{
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		perfils = perfilManager.findAll();
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaAux = parametrosDoSistemaManager.findById(parametrosDoSistema.getId());

		if(StringUtils.isBlank(parametrosDoSistema.getEmailPass()))
			parametrosDoSistema.setEmailPass(parametrosDoSistemaAux.getEmailPass());

		if (parametrosDoSistemaAux.getModulos() != null)
			parametrosDoSistema.setModulos(parametrosDoSistemaAux.getModulos());

		parametrosDoSistemaManager.update(parametrosDoSistema);
		
		perfils = perfilManager.findAll();
		addActionMessage("Configurações do Sistema atualizadas com sucesso!");

		return Action.SUCCESS;
	}
	
	public String listCamposCandidato() throws Exception
	{
		habilitaCampoExtra = getEmpresaSistema().isCampoExtraCandidato();
		if(habilitaCampoExtra)
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});		
				
		parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);
		return Action.SUCCESS;
	}
	
	public String prepareDeleteSemCodigoAC() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public String updateCamposCandidato() throws Exception
	{
		ParametrosDoSistema parametrosDoSistemaTmp = parametrosDoSistemaManager.findById(1L);
		
		parametrosDoSistemaTmp.setCamposCandidatoObrigatorio(StringUtil.converteArrayToString(camposCandidatoObrigatorios));
		parametrosDoSistemaTmp.setCamposCandidatoVisivel(StringUtil.converteArrayToString(camposCandidatoVisivels));
		parametrosDoSistemaTmp.setCamposCandidatoTabs(parametrosDoSistema.getCamposCandidatoTabs());
		
		parametrosDoSistemaManager.update(parametrosDoSistemaTmp);
		
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

	public String[] getCamposCandidatoObrigatorios() {
		return camposCandidatoObrigatorios;
	}

	public void setCamposCandidatoObrigatorios(String[] camposCandidatoObrigatorios) {
		this.camposCandidatoObrigatorios = camposCandidatoObrigatorios;
	}

	public String[] getCamposCandidatoVisivels() {
		return camposCandidatoVisivels;
	}

	public void setCamposCandidatoVisivels(String[] camposCandidatoVisivels) {
		this.camposCandidatoVisivels = camposCandidatoVisivels;
	}

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager) {
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}

	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}

}