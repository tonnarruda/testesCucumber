package com.fortes.rh.business.geral;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface QuantidadeLimiteColaboradoresPorCargoManager extends GenericManager<QuantidadeLimiteColaboradoresPorCargo>
{
	void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional);
	void updateLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId);
	void deleteByArea(Long areaId);
	void validaLimite(Long areaId, Long faixaId, Date data, Long empresaId) throws LimiteColaboradorExceditoException;
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByEmpresa(Long empresaId)throws Exception;
}
