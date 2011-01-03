package com.fortes.rh.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.dao.captacao.AtitudeDao;
import com.fortes.web.tags.CheckBox;

public class AtitudeManagerImpl extends GenericManagerImpl<Atitude, AtitudeDao> implements AtitudeManager
{
	
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
}