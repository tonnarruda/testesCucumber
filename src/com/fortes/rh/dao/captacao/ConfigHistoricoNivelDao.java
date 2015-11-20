package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfigHistoricoNivel;

public interface ConfigHistoricoNivelDao extends GenericDao<ConfigHistoricoNivel> 
{
	Collection<ConfigHistoricoNivel> findByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
	
	Collection<ConfigHistoricoNivel> findByEmpresaAndDataNivelCompetenciaHistorico(Long empresaId, Date data);

	void removeByNivelConfiguracaoHistorico(Long nivelConfiguracaoHIstoricoId);

	void removeNotIds(Long[] configHistoricoNiveisIds, Long nivelConfiguracaoHIstoricoId);
}
