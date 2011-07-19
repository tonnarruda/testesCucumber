package com.fortes.rh.dao.geral;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;

public interface QuantidadeLimiteColaboradoresPorCargoDao extends GenericDao<QuantidadeLimiteColaboradoresPorCargo> 
{
	void save(AreaOrganizacional areaOrganizacional, Cargo cargo, int limite);
}
