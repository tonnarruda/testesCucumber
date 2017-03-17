package com.fortes.rh.web.action.geral;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class ConfiguracaoCampoExtraEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	@Autowired private EmpresaManager empresaManager;
	
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras;
	private int[] ordens = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	private boolean habilitaCampoExtraColaborador;
	private boolean habilitaCampoExtraCandidato;
	private Collection<Empresa> empresas;
	private Empresa empresa;
		
	public String prepareUpdate() throws Exception
	{
		Long empresaId = getEmpresaSistema().getId();
		
		Empresa emp = empresaManager.findByIdProjection(empresaId);//tem que pegar do banco, pois pode ter mudado
		habilitaCampoExtraColaborador = emp.isCampoExtraColaborador();
		habilitaCampoExtraCandidato = emp.isCampoExtraCandidato();
		
		boolean atualizaTodas = configuracaoCampoExtraManager.atualizaTodas();
		
		if(empresa == null)//veio do menu
		{
			if (atualizaTodas)
			{
				empresa = new Empresa();
				empresa.setId(-1L);
			}
			else
				empresa = getEmpresaSistema();
		}
		else //selecionou todas ou uma empresa
		{
			if(empresa.getId() == null || empresa.getId().equals(-1))
			{
				empresa = new Empresa();
				empresa.setId(-1L);
				if(!atualizaTodas)
				{
					empresaId = -1L;
					habilitaCampoExtraColaborador = false;
					habilitaCampoExtraCandidato = false;
				}
			}
			else
			{
				empresaId = empresa.getId();
				emp = empresaManager.findByIdProjection(empresaId);
				habilitaCampoExtraColaborador = emp.isCampoExtraColaborador();
				habilitaCampoExtraCandidato = emp.isCampoExtraCandidato();
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
			empresaManager.atualizaCamposExtras(configuracaoCampoExtras, empresa, habilitaCampoExtraColaborador, habilitaCampoExtraCandidato);
			SecurityUtil.setEmpresaSession(ActionContext.getContext().getSession(), empresaManager.findById(getEmpresaSistema().getId()));

			addActionSuccess("Configurações gravadas com sucesso.");
			prepareUpdate();
			return Action.SUCCESS;
		} catch (Exception e) {
			addActionError("Erro ao salvar configurações.");
			e.printStackTrace();
			prepareUpdate();
			return Action.INPUT;
		}
		
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

	public boolean isHabilitaCampoExtraColaborador() {
		return habilitaCampoExtraColaborador;
	}

	public void setHabilitaCampoExtraColaborador(boolean habilitaCampoExtra) {
		this.habilitaCampoExtraColaborador = habilitaCampoExtra;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isHabilitaCampoExtraCandidato() {
		return habilitaCampoExtraCandidato;
	}

	public void setHabilitaCampoExtraCandidato(boolean habilitaCampoExtraCandidato) {
		this.habilitaCampoExtraCandidato = habilitaCampoExtraCandidato;
	}
}