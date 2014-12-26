package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.model.AbstractModel;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface QuantidadeLimiteColaboradoresPorCargoDao extends GenericDao<QuantidadeLimiteColaboradoresPorCargo> 
{
	void save(AreaOrganizacional areaOrganizacional, Cargo cargo, int limite);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByEntidade(Long id, Class<? extends AbstractModel> entidade);
	void deleteByArea(Long... areaId);
	void deleteByCargo(Long cargoId);
	QuantidadeLimiteColaboradoresPorCargo findLimite(Long cargoId, Collection<Long> areasIds);
	Collection<QuantidadeLimiteColaboradoresPorCargo> findByEmpresa(Long empresaId);
}
