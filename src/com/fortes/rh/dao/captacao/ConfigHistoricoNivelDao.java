package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public interface ConfigHistoricoNivelDao extends GenericDao<ConfigHistoricoNivel> 
{
	Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);

	void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId);
}
