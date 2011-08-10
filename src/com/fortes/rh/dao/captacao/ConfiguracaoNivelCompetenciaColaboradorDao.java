package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;

public interface ConfiguracaoNivelCompetenciaColaboradorDao extends GenericDao<ConfiguracaoNivelCompetenciaColaborador> 
{
	ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId);

	Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId);

	ConfiguracaoNivelCompetenciaColaborador checarHistoricoMesmaData(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);
}
