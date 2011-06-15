package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class AtitudeManagerImpl extends GenericManagerImpl<Atitude, AtitudeDao> implements AtitudeManager
{
	AreaOrganizacionalManager areaOrganizacionalManager;
	
	public Collection<Atitude> populaAtitudes(String[] atitudesCheck)
	{
		Collection<Atitude> atitudes = new ArrayList<Atitude>();

		if(atitudesCheck != null && atitudesCheck.length > 0)
		{
			Long atitudesIds[] = LongUtil.arrayStringToArrayLong(atitudesCheck);

			Atitude atitude;
			for (Long atitudeId: atitudesIds)
			{
				atitude = new Atitude();
				atitude.setId(atitudeId);

				atitudes.add(atitude);
			}
		}

		return atitudes;
	}

	public Collection<CheckBox> populaCheckOrderNome(long empresaId)
	{
		Collection<CheckBox> checks = new ArrayList<CheckBox>();
		try
		{
			Collection<Atitude> atitudes = getDao().findAllSelect(empresaId);
			checks = CheckListBoxUtil.populaCheckListBox(atitudes, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return checks;
	}
	
	public Collection<Atitude> findByCargo(Long cargoId)
	{
		return getDao().findByCargo(cargoId);
	}

	public Collection<Atitude> findByAreasOrganizacionalIds(Long[] areaOrganizacionalIds, Long empresasId) 
	{
		return getDao().findByAreasOrganizacionalIds(areaOrganizacionalIds, empresasId);
	}

	public Collection<Atitude> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}
	
	public void sincronizar(Long empresaOrigemId, Long empresaDestinoId, Map<Long, Long> areaIds, Map<Long, Long> conhecimentoIds) 
	{
		
		Collection<Atitude> atitudesDeOrigem = getDao().findSincronizarAtitudes(empresaOrigemId);
		
		for (Atitude atitude : atitudesDeOrigem)
		{
			Long atitudeOrigemId = atitude.getId();
			clonar(atitude, empresaDestinoId);
			conhecimentoIds.put(atitudeOrigemId, atitude.getId());
			
			Collection<AreaOrganizacional> areas = areaOrganizacionalManager.findByAtitude(atitudeOrigemId);
			popularAreasComIds(areaIds, areas);
			
			atitude.setAreaOrganizacionals(areas);
			update(atitude);
		}
	}

	private void popularAreasComIds(Map<Long, Long> areaIds, Collection<AreaOrganizacional> areas) 
	{
		
		for (AreaOrganizacional areaOrganizacional : areas)
		{
			Long id = areaIds.get( areaOrganizacional.getId() );
			
			if (id == null)
				continue;
			
			areaOrganizacional.setId(id);
		}
	}

	private void clonar(Atitude atitude, Long empresaDestinoId) 
	{
		
		atitude.setId(null);
		atitude.setAreaOrganizacionals(null);
		atitude.setEmpresaId(empresaDestinoId);
		
		getDao().save(atitude);
	}

	public void setAreaOrganizacionalManager(
			AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}
}