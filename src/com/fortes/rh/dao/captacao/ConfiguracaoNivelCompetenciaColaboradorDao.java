package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaColaboradorDao extends GenericDao<ConfiguracaoNivelCompetenciaColaborador> 
{
	ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId);

	Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId);

	ConfiguracaoNivelCompetenciaColaborador checarHistoricoMesmaData(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);

	void removeColaborador(Colaborador colaborador);

	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
}
