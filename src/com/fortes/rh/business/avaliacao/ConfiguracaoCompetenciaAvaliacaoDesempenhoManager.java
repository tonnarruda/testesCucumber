package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.ConfiguracaoCompetenciaAvaliacaoDesempenho;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;

public interface ConfiguracaoCompetenciaAvaliacaoDesempenhoManager extends GenericManager<ConfiguracaoCompetenciaAvaliacaoDesempenho> 
{
	public void save(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId);
	public void removeNotIn(Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> configuracaoCompetenciaAvaliacaoDesempenhos, Long avaliacaoDesempenhoId) throws Exception;
	public Collection<ConfiguracaoCompetenciaAvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Long faixaSalarialId, Long avaliacaoDesempenhoId);
	public void reajusteByConfiguracaoNivelCompetenciaFaixaSalarial(Collection<Competencia> conhecimentosAnteriores, Collection<Competencia> habilidadesAnteriores, Collection<Competencia> atitudesAnteriores, Collection<Competencia> competencias, ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial, Empresa empresa);
	public void replaceConfiguracaoNivelCompetenciaFaixaSalarial(ConfiguracaoNivelCompetenciaFaixaSalarial configuracaoNivelCompetenciaFaixaSalarial);
	public Collection<FaixaSalarial> findFaixasSalariaisByCompetenciasConfiguradasParaAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existeNovoHistoricoDeCompetenciaParaFaixaSalarialDeAlgumAvaliado(Long avaliacaoDesempenhoId);
	public void removeByAvaliacaoDesempenho(Long avaliacaoDesempenhoId);
	public boolean existe(Long configuracaoNivelCompetenciaFaixaSalarialId,Long avaliacaoDesempenhoId);
	public Collection<Colaborador> findColabSemCompetenciaConfiguradaByAvalDesempenhoId(Long avaliacaoDesempenhoId);
}
