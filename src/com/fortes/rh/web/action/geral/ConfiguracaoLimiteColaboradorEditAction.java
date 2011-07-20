package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ConfiguracaoLimiteColaboradorEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CargoManager cargoManager;
	
	private ConfiguracaoLimiteColaborador configuracaoLimiteColaborador;
	private Collection<ConfiguracaoLimiteColaborador> configuracaoLimiteColaboradors;
	private Collection<AreaOrganizacional> areaOrganizacionais;
	private Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos;
	private Collection<Cargo> cargos;
	private Long[] idsFamiliasAreasJaConfiguradas;
	private Long empresaId;

	private void prepare() throws Exception
	{
		areaOrganizacionais = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA);

		if(configuracaoLimiteColaborador != null && configuracaoLimiteColaborador.getId() != null)
		{
			configuracaoLimiteColaborador = (ConfiguracaoLimiteColaborador) configuracaoLimiteColaboradorManager.findById(configuracaoLimiteColaborador.getId());
			configuracaoLimiteColaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionais, configuracaoLimiteColaborador.getAreaOrganizacional().getId()));
		}
		
		Collection<Long> idsAreasConfiguradas = configuracaoLimiteColaboradorManager.findIdAreas(getEmpresaSistema().getId());
		idsFamiliasAreasJaConfiguradas = areaOrganizacionalManager.selecionaFamilia(areaOrganizacionais, idsAreasConfiguradas);
		
		empresaId = getEmpresaSistema().getId();
		cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nomeMercado");
		//Collection<QuantidadeLimiteColaboradoresPorCargo> quatidades = configuracaoLimiteColaboradorManager.findLimiteByArea(1L);
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		quantidadeLimiteColaboradoresPorCargos = quantidadeLimiteColaboradoresPorCargoManager.findByArea(configuracaoLimiteColaborador.getAreaOrganizacional().getId());
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			configuracaoLimiteColaboradorManager.save(configuracaoLimiteColaborador);
			quantidadeLimiteColaboradoresPorCargoManager.saveLimites(quantidadeLimiteColaboradoresPorCargos, configuracaoLimiteColaborador.getAreaOrganizacional());
			addActionMessage("Configuração gravada com sucesso.");
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível incluir esta configuração.");
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		configuracaoLimiteColaboradorManager.update(configuracaoLimiteColaborador);
		quantidadeLimiteColaboradoresPorCargoManager.updateLimites(quantidadeLimiteColaboradoresPorCargos, configuracaoLimiteColaborador.getAreaOrganizacional());
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		configuracaoLimiteColaboradors = configuracaoLimiteColaboradorManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			quantidadeLimiteColaboradoresPorCargoManager.deleteByArea(configuracaoLimiteColaborador.getAreaOrganizacional().getId());
			configuracaoLimiteColaboradorManager.remove(configuracaoLimiteColaborador.getId());
			addActionMessage("Configuração excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta configuração.");
		}

		return list();
	}
	
	public ConfiguracaoLimiteColaborador getConfiguracaoLimiteColaborador()
	{
		if(configuracaoLimiteColaborador == null)
			configuracaoLimiteColaborador = new ConfiguracaoLimiteColaborador();
		return configuracaoLimiteColaborador;
	}

	public void setConfiguracaoLimiteColaborador(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador)
	{
		this.configuracaoLimiteColaborador = configuracaoLimiteColaborador;
	}

	public void setConfiguracaoLimiteColaboradorManager(ConfiguracaoLimiteColaboradorManager configuracaoLimiteColaboradorManager)
	{
		this.configuracaoLimiteColaboradorManager = configuracaoLimiteColaboradorManager;
	}
	
	public Collection<ConfiguracaoLimiteColaborador> getConfiguracaoLimiteColaboradors()
	{
		return configuracaoLimiteColaboradors;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionais() {
		return areaOrganizacionais;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> getQuantidadeLimiteColaboradoresPorCargos() {
		return quantidadeLimiteColaboradoresPorCargos;
	}

	public void setQuantidadeLimiteColaboradoresPorCargos(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos) {
		this.quantidadeLimiteColaboradoresPorCargos = quantidadeLimiteColaboradoresPorCargos;
	}

	public Collection<Cargo> getCargos() {
		return cargos;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) {
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public Long[] getIdsFamiliasAreasJaConfiguradas() {
		return idsFamiliasAreasJaConfiguradas;
	}
}
