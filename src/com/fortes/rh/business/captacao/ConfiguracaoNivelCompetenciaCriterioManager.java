package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCriterio;

public interface ConfiguracaoNivelCompetenciaCriterioManager extends GenericManager<ConfiguracaoNivelCompetenciaCriterio>
{
	public Collection<ConfiguracaoNivelCompetenciaCriterio> findByConfiguracaoNivelCompetencia(Long configuracaoNivelCompetenciaId);
	public void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
}