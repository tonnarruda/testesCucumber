package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.dao.geral.QuantidadeLimiteColaboradoresPorCargoDao;

public class QuantidadeLimiteColaboradoresPorCargoManagerImpl extends GenericManagerImpl<QuantidadeLimiteColaboradoresPorCargo, QuantidadeLimiteColaboradoresPorCargoDao> implements QuantidadeLimiteColaboradoresPorCargoManager
{
	public void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional) 
	{
		for (QuantidadeLimiteColaboradoresPorCargo limite : quantidadeLimiteColaboradoresPorCargos) 
		{
			if(limite != null)//javascrip pode mandar obj null dentro do array
			{
				limite.setAreaOrganizacional(areaOrganizacional);
				getDao().save(limite);				
			}
		}
	}

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId) 
	{
		return getDao().findByArea(areaId);
	}

	public void updateLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional) 
	{
		getDao().deleteByArea(areaOrganizacional.getId());
		saveLimites(quantidadeLimiteColaboradoresPorCargos, areaOrganizacional);
	}

	public void deleteByArea(Long areaId) 
	{
		getDao().deleteByArea(areaId);
	}
}
