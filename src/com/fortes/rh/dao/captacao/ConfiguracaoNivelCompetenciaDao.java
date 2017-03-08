package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaCandidato;
import com.fortes.rh.model.geral.Colaborador;

public interface ConfiguracaoNivelCompetenciaDao extends GenericDao<ConfiguracaoNivelCompetencia> 
{
	void deleteConfiguracaoByCandidatoFaixa(Long candidatoId, Long solicitacaoId);
	Collection<ConfiguracaoNivelCompetencia> findByFaixa(Long faixaSalarialId, Date data);
	Collection<ConfiguracaoNivelCompetencia> findByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
	public Collection<ConfiguracaoNivelCompetencia> findByCandidato(Long candidatoId);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaColaborador(Long[] competenciasIds, Long configuracaoNivelCompetenciaColaboradorId, Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId);
	void deleteByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void deleteByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaByFaixaSalarial(Long faixaId, Date data, Long configuracaoNivelCompetenciaFaixaSalarialId, Long avaliadorId, Long avaliacaoDesempenhoId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciaColaborador(Date dataIni, Date dataFim, Long[] competenciasIds, Long faixaSalarialColaboradorId, boolean ordenarPorNivel);
	void removeByFaixas(Long[] faixaSalarialIds);
	void removeColaborador(Colaborador colaborador);
	void removeByConfiguracaoNivelCompetenciaColaborador(Long configuracaoNivelCompetenciaColaboradorId);
	void removeByConfiguracaoNivelCompetenciaFaixaSalarial(Long configuracaoNivelCompetenciaFaixaSalarialId);
	void removeByCandidato(Long candidatoId);
	Long[] findCompetenciasIdsConfiguradasByFaixaSolicitacao(Long faixaSalarialId);
	Integer somaConfiguracoesByFaixa(Long faixaSalarialId);
	Collection<ConfiguracaoNivelCompetencia> findColaboradoresCompetenciasAbaixoDoNivel(Long empresaId, Long[] estabelecimentoIds, Long[] areaIds, Boolean colaboradoresAvaliados, char agruparPor);
	void removeDependenciasComConfiguracaoNivelCompetenciaColaboradorByFaixasSalariais(Long[] faixaIds);
	void removeDependenciasComConfiguracaoNivelCompetenciaFaixaSalarialByFaixasSalariais(Long[] faixaIds);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciasConfiguracaoNivelCompetenciaFaixaSalarial(Long[] competenciaIds, Long configuracaoNivelCompetenciaFaixaSalarialId);
	Collection<Competencia> findCompetenciasColaboradorByFaixaSalarialAndPeriodo(Long faixaId, Date dataIni, Date dataFim);
	Collection<ConfiguracaoNivelCompetenciaCandidato> candsComDependenciaComCompetenciasDoCandidato(Long faixaSalarialId, Date dataInicial, Date dataFinal);
	Collection<Colaborador> findDependenciaComColaborador(Long faixaSalarialId,	Date data);
	Collection<Candidato> findDependenciaComCandidato(Long faixaSalarialId, Date data);
	void removeByCandidatoAndSolicitacao(Long candidatoId, Long solicitacaoId);
	void removeBySolicitacaoId(Long solicitacaoId);
	Collection<ConfiguracaoNivelCompetencia> findConfiguracaoNivelCompetenciaCandidato(Long configuracaoNivelCompetenciaCandidatoId);
	Collection<ConfiguracaoNivelCompetencia> findCompetenciasAndPesos(Long avaliacaoDesempenhoId, Long avaliadoId);
	LinkedList<ConfiguracaoNivelCompetencia> findByAvaliacaoDesempenhoAndAvaliado(Long avaliacaoDesempenhoId, Long avaliadoId, boolean agruparPorCargo);
}
