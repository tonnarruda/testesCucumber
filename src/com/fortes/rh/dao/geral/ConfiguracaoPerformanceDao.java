package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.ConfiguracaoPerformance;

public interface ConfiguracaoPerformanceDao extends GenericDao<ConfiguracaoPerformance> 
{

	void removeByUsuario(Long usuarioId);

	Collection<ConfiguracaoPerformance> findByUsuario(Long id);

}
