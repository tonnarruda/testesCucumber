package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface ConfiguracaoLimiteColaboradorManager extends GenericManager<ConfiguracaoLimiteColaborador>
{
	Collection<QuantidadeLimiteColaboradoresPorCargo> findLimiteByArea(long areaId);
	void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Long areaId);
}
