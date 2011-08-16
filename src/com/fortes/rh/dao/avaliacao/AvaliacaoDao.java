package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.Avaliacao;

public interface AvaliacaoDao extends GenericDao<Avaliacao> 
{
	Collection<Avaliacao> findAllSelect(Long empresaId, Boolean ativo, char modeloAvaliacao, String titulo);
	Integer getPontuacaoMaximaDaPerformance(Long avaliacaoId);
	Collection<Avaliacao> findPeriodoExperienciaIsNull(char acompanhamentoExperiencia, Long empresaId);
	Collection<Avaliacao> findAllSelectComAvaliacaoDesempenho(Long empresaId, boolean ativa);
}