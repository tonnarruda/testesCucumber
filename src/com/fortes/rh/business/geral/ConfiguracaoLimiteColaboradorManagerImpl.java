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

	public Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Long> findIdAreas(Long empresaId) {
		return getDao().findIdAreas(empresaId);
	}
}
