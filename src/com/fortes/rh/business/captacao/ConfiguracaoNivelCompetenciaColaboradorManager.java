package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaColaboradorManager extends GenericManager<ConfiguracaoNivelCompetenciaColaborador>
{
	ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId);
	Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId);
	void removeColaborador(Colaborador colaborador);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	void deleteDependenciasByFaixaSalarial(Long[] faixaIds);
	ConfiguracaoNivelCompetenciaColaborador findByData(Date data, Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId);
	void verificaAvaliadorAnonimo(ConfiguracaoNivelCompetenciaColaborador configuracaoNivelCompetenciaColaborador);
}
