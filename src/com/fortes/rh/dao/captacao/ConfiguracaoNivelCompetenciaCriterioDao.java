package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;

public interface ConfiguracaoNivelCompetenciaCriterioDao extends GenericDao<ConfiguracaoNivelCompetenciaCriterio> 
{
	public Collection<ConfiguracaoNivelCompetenciaCriterio> findByConfiguracaoNivelCompetencia(Long configuracaoNivelCompetenciaId, Long configuracaoNivelCompetenciaFaixaSalarialId);
	public void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
}
