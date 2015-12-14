package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaColaboradorDao extends GenericDao<ConfiguracaoNivelCompetenciaColaborador> 
{
	ConfiguracaoNivelCompetenciaColaborador findByIdProjection(Long configuracaoNivelCompetenciaColaboradorId);
	Collection<ConfiguracaoNivelCompetenciaColaborador> findByColaborador(Long colaboradorId);
	void removeColaborador(Colaborador colaborador);
	void deleteByFaixaSalarial(Long[] faixaIds) throws Exception;
	ConfiguracaoNivelCompetenciaColaborador findByData(Date data, Long colaboradorId, Long avaliadorId, Long colaboradorQuestionarioId);
	boolean existeDependenciaComCompetenciasDaFaixaSalarial(Long faixaSalarialId, Date dataInicial, Date dataFinal);
	boolean existeConfigCompetenciaParaAFaixaDestehistorico(Long historicoColaboradorId);
	Collection<ConfiguracaoNivelCompetenciaColaborador> findByDataAndFaixaSalarial(Date dataInicio, Date dataFim, Long faixaSalarialId);
}
