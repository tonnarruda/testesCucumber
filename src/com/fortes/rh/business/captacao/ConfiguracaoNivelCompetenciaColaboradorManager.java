package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;

public interface ConfiguracaoNivelCompetenciaColaboradorManager extends GenericManager<ConfiguracaoNivelCompetenciaColaborador>
{
	ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId);

	Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId);

	void checarHistoricoMesmaData(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador) throws Exception;
}
