package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ConfiguracaoCampoExtraEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private EmpresaManager empresaManager;
	
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras;
	private int[] ordens = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,16};
	private boolean habilitaCampoExtra;
	private Collection<Empresa> empresas;
	private Empresa empresa;
		
	public String prepareUpdate() throws Exception
	{
		Long empresaId = getEmpresaSistema().getId();
		habilitaCampoExtra = empresaManager.findByIdProjection(getEmpresaSistema().getId()).isCampoExtraColaborador();//tem que pegar do banco, pois pode ter mudado
		boolean atualizaTodas = configuracaoCampoExtraManager.atualizaTodas();
		
		if(empresa == null)//veio do menu
		{
			if (atualizaTodas)
				empresa = EmpresaFactory.getEmpresa(-1L);
			else
				empresa = getEmpresaSistema();
		}
		else //selecionou todas ou uma empresa
		{
			if(empresa.getId() == null || empresa.getId().equals(-1))
			{
				empresa = EmpresaFactory.getEmpresa(-1L);
				if(!atualizaTodas)
				{
					empresaId = -1L;
					habilitaCampoExtra = false;
				}
			}
			else
			{
				empresaId = empresa.getId();
				habilitaCampoExtra = empresaManager.findById(empresaId).isCampoExtraColaborador();
			}
		}
		
		configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"empresa.id"}, new Object[]{empresaId}, new String[]{"posicao"});
		if(configuracaoCampoExtras.isEmpty())//empresa ainda não configurada
			configuracaoCampoExtras = configuracaoCampoExtraManager.findAllSelect(null);
			
		empresas = empresaManager.findTodasEmpresas();
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			empresaManager.atualizaCamposExtras(configuracaoCampoExtras, empresa, habilitaCampoExtra);
			addActionMessage("Configurações gravadas com sucesso!");
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Erro ao salvar configurações.");
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}
		
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

	public boolean isHabilitaCampoExtra() {
		return habilitaCampoExtra;
	}

	public void setHabilitaCampoExtra(boolean habilitaCampoExtra) {
		this.habilitaCampoExtra = habilitaCampoExtra;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
