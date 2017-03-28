package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.Avaliacao;

public interface AvaliacaoDao extends GenericDao<Avaliacao> 
{
	Collection<Avaliacao> findAllSelect(Integer page, Integer pagingSize, Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo);
	Integer getCount(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo);
	Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId, Long... perguntaIds);
	Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId);
	Collection<Avaliacao> findAllSelectComAvaliacaoDesempenho(Long empresaId, boolean ativa);
	Collection<Avaliacao> findModelosPeriodoExperienciaAtivosAndModelosConfiguradosParaOColaborador(Long empresaId, Long colaboradorId);
	Collection<Avaliacao> findModelosAcompanhamentoPeriodoExperiencia(boolean ativo, Long empresaId, Long colaboradorId, Long colaboradorLogadoId, Integer tipoResponsavel);
}