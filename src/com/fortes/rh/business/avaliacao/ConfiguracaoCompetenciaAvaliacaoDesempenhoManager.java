package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public interface ConfiguracaoCompetenciaAvaliacaoDesempenhoManager extends GenericManager<ConfiguracaoCompetenciaAvaliacaoDesempenho> 
{
	public void save(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId);
	public void removeNotIn(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId) throws Exception;
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId);
	public void reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(Collection<Competencia> competenciasAnteriores, Collection<Competencia> competencias, Character tipoCompetencia, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial);
}
