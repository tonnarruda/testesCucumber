package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface ConfiguracaoLimiteColaboradorDao extends GenericDao<ConfiguracaoLimiteColaborador> 
{

	Collection<QuantidadeLimiteColaboradoresPorCargo> findLimiteByArea(long areaId);

	void saveLimites(Long areaId, Long cargoId, int limite);

}
