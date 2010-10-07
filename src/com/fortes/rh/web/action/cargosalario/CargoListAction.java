package com.fortes.rh.web.action.cargosalario;

import java.util.Collection;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
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

	private Collection<AreaOrganizacional> areas;

	public String list() throws Exception
	{
		setTotalSize(cargoManager.getCount(getEmpresaSistema().getId(), areaOrganizacional.getId(), cargo.getNomeMercado()));
		cargos = cargoManager.findCargos(getPage(), getPagingSize(), getEmpresaSistema().getId(), areaOrganizacional.getId(), cargo.getNomeMercado());

		areas = areaOrganizacionalManager.montaAllSelect(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		cargoManager.remove(cargo.getId(), getEmpresaSistema());
		addActionMessage("Cargo excluído com sucesso.");

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
}