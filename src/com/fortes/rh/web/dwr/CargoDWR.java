package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

@SuppressWarnings("unchecked")
public class CargoDWR
{
	private CargoManager cargoManager;

	public Map getCargoByGrupo(String[] grupoOcupacionalIds, Long empresaId)
	{
		Long [] idsLong = LongUtil.arrayStringToArrayLong(grupoOcupacionalIds);

		Collection<Cargo> cargos = cargoManager.findByGrupoOcupacionalIdsProjection(idsLong, empresaId);

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId","getNomeMercado");
	}

	public Map getCargoByArea(String[] areaOrganizacionalIds, String label, Long empresaId)
	{
		Long [] idsLong = LongUtil.arrayStringToArrayLong(areaOrganizacionalIds);

		Collection<Cargo> cargos = cargoManager.findByAreasOrganizacionalIdsProjection(idsLong, empresaId);

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", label);
	}
	
	public Map getByEmpresa(Long empresaId)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		String getParametro = "getId";
		
		if(empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado
		{
			cargos = cargoManager.findAllSelectDistinctNome();
			getParametro = "getNomeMercado";
		}
		else
			cargos = cargoManager.findAllSelect(empresaId, "nomeMercado");

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos, getParametro, "getNomeMercado");
	}
	
	public Map getByEmpresas(Long empresaId, Long[] empresaIds)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		String getParametro = "getId";
		
		if(empresaId == 0)
			cargos = cargoManager.findAllSelect(empresaIds);
		else
			cargos = cargoManager.findAllSelect(empresaId, "nomeMercado");
					
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos, getParametro, "getNomeMercadoComEmpresa");
	}
	
	public Map getByAreaDoHistoricoColaborador(String[] areaOrganizacionalIds)
	{
		return cargoManager.findByAreaDoHistoricoColaborador(areaOrganizacionalIds); 
	}

	public Collection<Cargo> getCargosByArea(Long areaOrganizacionalId, Long empresaId)
	{
		return cargoManager.findByArea(areaOrganizacionalId, empresaId);
	}
	
	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}
}
