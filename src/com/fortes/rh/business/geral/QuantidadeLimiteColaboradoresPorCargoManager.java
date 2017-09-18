package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface QuantidadeLimiteColaboradoresPorCargoManager extends GenericManager<QuantidadeLimiteColaboradoresPorCargo>
{
	void saveLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional);
	void updateLimites(Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, AreaOrganizacional areaOrganizacional);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByArea(Long areaId);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByCargo(Long cargoId);
	void deleteByArea(Long... areaId);
	void deleteByCargo(Long cargoId);
	void validaLimite(Long areaId, Long faixaId, Long empresaId, Long colaboradorId) throws LimiteColaboradorExcedidoException;
	QuantidadeLimiteColaboradoresPorCargo qtdLimiteColaboradorPorCargo(Long areaId, Long faixaId, Long empresaId, Long colaboradorId);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByEmpresa(Long empresaId)throws Exception;
}
