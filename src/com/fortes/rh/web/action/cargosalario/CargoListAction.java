package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class CargoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private CargoManager cargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	private Collection<Cargo> cargos;
	private Cargo cargo = new Cargo();
	private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
	private Map<String,Object> parametros;

	private Collection<AreaOrganizacional> areas;

	public String list() throws Exception
	{
		setTotalSize(cargoManager.getCount(getEmpresaSistema().getId(), areaOrganizacional.getId(), cargo.getNomeMercado(), cargo.isAtivo()));
		cargos = cargoManager.findCargos(getPage(), getPagingSize(), getEmpresaSistema().getId(), areaOrganizacional.getId(), cargo.getNomeMercado(), cargo.isAtivo());

		areas = areaOrganizacionalManager.montaAllSelect(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}
	
	public String imprimirLista() throws Exception
	{
		Long areaId = areaOrganizacional != null ? areaOrganizacional.getId() : null;
		
		cargos = cargoManager.findCargos(0, 0, getEmpresaSistema().getId(), areaId, cargo.getNomeMercado(), cargo.isAtivo());
		
		areaOrganizacional = areaOrganizacionalManager.findByIdProjection(areaId);
		
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Cargos", getEmpresaSistema(), (areaOrganizacional == null ? "" : "Área Organizacional: " + areaOrganizacional.getNome() + "\n") + "Ativos: " + (cargo.isAtivo() ? "Sim" : "Não"));
		
		if (cargos.isEmpty()) 
		{
			addActionMessage("Não existem dados para o filtro informado");
			list();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try {
			cargoManager.remove(cargo.getId(), getEmpresaSistema());
			addActionMessage("Cargo excluído com sucesso.");
		} catch (Exception e) {
			String message = "Erro ao excluir Cargo.<br/>";
			
			if(e.getCause().getClass() == ConstraintViolationException.class)
				message = "O Cargo não pode ser excluído, pois possui dependência.";
			else if(e.getMessage() != null)
				message += e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message += e.getCause().getLocalizedMessage();
			
			addActionError(message);
			
			list();
		}

		return Action.SUCCESS;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public Cargo getCargo()
	{
		if(cargo == null)
			cargo = new Cargo();
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Collection<AreaOrganizacional> getAreas()
	{		
		return areas;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}
}