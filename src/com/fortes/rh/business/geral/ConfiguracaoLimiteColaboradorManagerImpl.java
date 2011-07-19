package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManager;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;

public class ConfiguracaoLimiteColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoLimiteColaborador, ConfiguracaoLimiteColaboradorDao> implements ConfiguracaoLimiteColaboradorManager
{

	public Collection<QuantidadeLimiteColaboradoresPorCargo> findLimiteByArea(long areaId) 
	{
		return getDao().findLimiteByArea(areaId);
	}

	public void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Long areaId) 
	{
		for (QuantidadeLimiteColaboradoresPorCargo limite : quantidadeLimiteColaboradoresPorCargos) 
		{
			getDao().saveLimites(areaId, limite.getCargo().getId(), limite.getLimite());
		}
	}
}
