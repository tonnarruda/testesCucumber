package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;

public class HabilidadeDWR
{
	private HabilidadeManager habilidadeManager;

	@SuppressWarnings("deprecation")
	public Map getHabilidades(String[] areaOrganizacionalIds, Long empresaId)
	{
		Collection<Habilidade> habilidades;

		if (areaOrganizacionalIds != null && areaOrganizacionalIds.length > 0)
			habilidades =  habilidadeManager.findByAreasOrganizacionalIds(LongUtil.arrayStringToArrayLong(areaOrganizacionalIds),empresaId);
		else
			habilidades =  habilidadeManager.findAllSelect(empresaId);

		return new CollectionUtil<Habilidade>().convertCollectionToMap(habilidades,"getId","getNome");
	}

	public void setHabilidadeManager(HabilidadeManager habilidadeManager) {
		this.habilidadeManager = habilidadeManager;
	}

	
}
