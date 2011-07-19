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
			limite.setAreaOrganizacional(areaOrganizacional);
			getDao().save(limite);
		}
		
	}
}
