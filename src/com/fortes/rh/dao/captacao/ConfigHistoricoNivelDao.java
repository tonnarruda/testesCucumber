package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public interface ConfigHistoricoNivelDao extends GenericDao<ConfigHistoricoNivel> 
{
	Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
	
	Collection<ConfigHistoricoNivel> findByEmpresaAndDataDoNivelCompetenciaHistorico(Long empresaId, Date dataNivelCompetenciaHistorico);

	void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHistoricoId);

	void removeNotIn(Long[] configHistoricoNiveisIds, Long nivelConfiguracaoHistoricoId);
}
