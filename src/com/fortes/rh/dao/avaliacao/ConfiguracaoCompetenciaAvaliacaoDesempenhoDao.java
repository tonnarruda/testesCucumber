package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoCompetenciaAvaliacaoDesempenhoDao extends GenericDao<ConfiguracaoCompetenciaAvaliacaoDesempenho> 
{
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId);
	public void replaceConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial);
	public void removeByCompetenciasQueNaoPermaneceram(Long[] competenciasIds, Long faixaSalarialId, Character tipo);
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId);
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId,Long avaliacaoDesempenhoId);
	public Collection<Colaborador> findColabSemCompetenciaConfiguradaByAvalDesempenhoId(Long avaliacaoDesempenhoId);
}