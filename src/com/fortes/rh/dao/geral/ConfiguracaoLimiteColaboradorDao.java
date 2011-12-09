package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;

public interface ConfiguracaoLimiteColaboradorDao extends GenericDao<ConfiguracaoLimiteColaborador> 
{
	Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId);
	Collection<Long> findIdAreas(Long empresaId);
	void deleteByAreaOrganizacional(Long[] areaIds) throws Exception;
}
