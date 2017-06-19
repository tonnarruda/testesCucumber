package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CargoDWR
{
	private CargoManager cargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;

	public Map getCargoByGrupo(String[] grupoOcupacionalIds, Long empresaId)
	{
		Collection<Cargo> cargos = getCargosByGrupoCollection(grupoOcupacionalIds, empresaId, null);

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId","getNomeMercadoComStatus");
	}

	public Map getCargoByGrupoAtivoInativo(String naoApagar, HttpServletRequest request, String[] grupoOcupacionalIds, Long empresaId, Character ativo)
	{
		Boolean cargoAtivo = null;
		if(ativo == 'A')
			cargoAtivo = true;
		if(ativo == 'I')
			cargoAtivo = false;
		
		Map session = new SessionMap(request);
		boolean verTodasAreas = SecurityUtil.verifyRole(session, new String[]{"ROLE_VER_AREAS"});
		
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		if(verTodasAreas){
			cargos = getCargosByGrupoCollection(grupoOcupacionalIds, empresaId, cargoAtivo);
		}else{
			Long[] areasIds = new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areaOrganizacionalManager.findAllListAndInativasByUsuarioId(empresaId, SecurityUtil.getIdUsuarioLoged(session), AreaOrganizacional.TODAS, null));
			cargos = cargoManager.findByAreasAndGrupoOcapcinal(empresaId, LongUtil.arrayStringToArrayLong(grupoOcupacionalIds), cargoAtivo, areasIds);
		}
		
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId","getNomeMercadoComStatus");
	}
	
	private Collection<Cargo> getCargosByGrupoCollection (String[] grupoOcupacionalIds, Long empresaId, Boolean cargoAtivo)
	{
		Long [] idsLong = LongUtil.arrayStringToArrayLong(grupoOcupacionalIds);
		
		Collection<Cargo> cargos = cargoManager.findByGrupoOcupacionalIdsProjection(idsLong, empresaId, cargoAtivo);
		
		return cargos;
	}
	
	private Collection<Cargo> getCargosByArea(String[] areaOrganizacionalIds, Long empresaId) {
		Long [] idsLong = LongUtil.arrayStringToArrayLong(areaOrganizacionalIds);
		
		if(empresaId != null && (empresaId.equals(-1L) || empresaId.equals(0)))
			empresaId = null;
		
		Collection<Cargo> cargos = cargoManager.findByAreasOrganizacionalIdsProjection(idsLong, empresaId);
		return cargos;
	}

	public Map getCargoByArea(String[] areaOrganizacionalIds, String label, Long empresaId)
	{
		Collection<Cargo> cargos = getCargosByArea(areaOrganizacionalIds, empresaId);
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", label);
	}
	
	public Map getCargoByGruposMaisSemGruposRelacionado(String[] gruposIds, Long empresaId)
	{
		Collection<Cargo> cargos = getCargosByGrupoCollection(gruposIds, empresaId, null);
		
		Collection<Cargo> cargosSemGrupoRelacionada = cargoManager.getCargosSemGrupoRelacionado(empresaId); 						
		
		CollectionUtil<Cargo> cUtil = new CollectionUtil<Cargo>();
		
		if(cargosSemGrupoRelacionada != null && cargosSemGrupoRelacionada.size() > 0)
		{
			cargos.addAll(cargosSemGrupoRelacionada);
			cUtil.sortCollectionStringIgnoreCase(cargos, "nomeMercado");
		}
		
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", "getNomeMercadoComStatus");
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
	
	public Map getByEmpresa(Long empresaId, Long[] empresaIds)
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		String getParametro = "getId";
		
		if(empresaId == -1)//Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado
		{
			cargos = cargoManager.findAllSelectDistinctNome(empresaIds);
			getParametro = "getNomeMercadoComStatus";
		}
		else
			cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, empresaId);

		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos, getParametro, "getNomeMercadoComStatus");
	}
	
	private Collection<Cargo> getFindAllSelect(Long empresaId, Long[] empresaIds) 
	{
		Collection<Cargo> cargos = new ArrayList<Cargo>();
		
		if(empresaId == 0 || empresaId == -1)
			cargos = cargoManager.findAllSelect(empresaIds);
		else
			cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, empresaId);
		return cargos;
	}
	
	public Map getByEmpresas(Long empresaId, Long[] empresaIds)
	{
		Collection<Cargo> cargos = getFindAllSelect(empresaId, empresaIds);
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos, "getId", "getNomeMercadoComEmpresaEStatus");
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
			cUtil.sortCollectionStringIgnoreCase(cargos, "nomeMercadoComEmpresaEStatus");
		}
		
		return cUtil.convertCollectionToMap(cargos, getParametro, "getNomeMercadoComEmpresaEStatus");
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
		return cargoManager.findByAreasAndGrupoOcapcinal(empresaId, null, null, areaOrganizacionalId);
	}
	
	public Map<Long, String> getCargosByAreaGrupo(Long[] areaOrganizacionalIds, Long[] grupoOcupacionalIds, Long empresaId)
	{
		Collection<Cargo> cargos = cargoManager.findByAreaGrupo(areaOrganizacionalIds, grupoOcupacionalIds, empresaId);
		return new CollectionUtil<Cargo>().convertCollectionToMap(cargos,"getId", "getNomeMercadoComEmpresaEStatus");
	}
	
	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}
