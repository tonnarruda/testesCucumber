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
	
	private Collection<Cargo> getCargosByArea(String[] areaOrganizacionalIds, Long empresaId) {
		Long [] idsLong = LongUtil.arrayStringToArrayLong(areaOrganizacionalIds);
		
		Collection<Cargo> cargos = cargoManager.findByAreasOrganizacionalIdsProjection(idsLong, empresaId);
		return cargos;
	}

	public Map getCargoByArea(String[] areaOrganizacionalIds, String label, Long empresaId)
	{
		Collection<Cargo> cargos = getCargosByArea(areaOrganizacionalIds, empresaId);
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", label);
	}
	
	public Map getCargoByAreaMaisSemAreaRelacionada(String[] areaOrganizacionalIds, String label, Long empresaId)
	{
		Collection<Cargo> cargos = getCargosByArea(areaOrganizacionalIds, empresaId);
		
		Collection<Cargo> cargosSemAreaRelacionada;
		
		if(empresaId == 0)
			cargosSemAreaRelacionada = cargoManager.getCargosSemAreaRelacionada(null); 			
		else
			cargosSemAreaRelacionada = cargoManager.getCargosSemAreaRelacionada(empresaId); 						
		
		CollectionUtil<Cargo> cUtil = new CollectionUtil<Cargo>();
		
		if(cargosSemAreaRelacionada != null && cargosSemAreaRelacionada.size() > 0)
		{
			cargos.addAll(cargosSemAreaRelacionada);
			cUtil.sortCollectionStringIgnoreCase(cargos, "nomeMercadoComEmpresa");
		}

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
	
	private Collection<Cargo> getFindAllSelect(Long empresaId, Long[] empresaIds) {
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		
		if(empresaId == 0)
			cargos = cargoManager.findAllSelect(empresaIds);
		else
			cargos = cargoManager.findAllSelect(empresaId, "nomeMercado");
		return cargos;
	}
	
	public Map getByEmpresas(Long empresaId, Long[] empresaIds)
	{
		String getParametro = "getId";
		Collection<Cargo> cargos = getFindAllSelect(empresaId, empresaIds);
		
		CollectionUtil<Cargo> cUtil = new CollectionUtil<Cargo>();
		
		return cUtil.convertCollectionToMap(cargos, getParametro, "getNomeMercadoComEmpresa");
	}
	
	public Map getByEmpresasMaisSemAreaRelacionada(Long empresaId, Long[] empresaIds)
	{
		String getParametro = "getId";
		Collection<Cargo> cargos = getFindAllSelect(empresaId, empresaIds);
		Collection<Cargo> cargosSemAreaRelacionada;
		
		if(empresaId == 0)
			cargosSemAreaRelacionada = cargoManager.getCargosSemAreaRelacionada(null); 			
		else
			cargosSemAreaRelacionada = cargoManager.getCargosSemAreaRelacionada(empresaId); 						
		
		CollectionUtil<Cargo> cUtil = new CollectionUtil<Cargo>();
		
		if(cargosSemAreaRelacionada != null && cargosSemAreaRelacionada.size() > 0)
		{
			cargos.addAll(cargosSemAreaRelacionada);
			cUtil.sortCollectionStringIgnoreCase(cargos, "nomeMercadoComEmpresa");
		}
		
		return cUtil.convertCollectionToMap(cargos, getParametro, "getNomeMercadoComEmpresa");
	}
	
	public boolean verificaCargoSemAreaRelacionada(Long empresaId)
	{
		if(empresaId == 0)
			return cargoManager.existemCargosSemAreaRelacionada(null);
		else
			return cargoManager.existemCargosSemAreaRelacionada(empresaId);
	}
	
	public Map getByAreaDoHistoricoColaborador(String[] areaOrganizacionalIds)
	{
		return cargoManager.findByAreaDoHistoricoColaborador(areaOrganizacionalIds); 
	}

	public Collection<Cargo> getCargosByArea(Long areaOrganizacionalId, Long empresaId)
	{
		return cargoManager.findByArea(areaOrganizacionalId, empresaId);
	}
	
	public Map<Long, String> getCargosByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId)
	{
		Collection<Cargo> cargos = cargoManager.findByAreaGrupo(areaOrganizacionalIds, grupoOcupacionalIds, empresaId);
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", "getNomeMercadoComEmpresa");
	}
	
	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}
}
