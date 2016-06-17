package com.fortes.rh.dao.avaliacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;

public interface AvaliacaoDesempenhoDao extends GenericDao<AvaliacaoDesempenho> 
{
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa, Character tipoModeloAvaliacao);
	public AvaliacaoDesempenho findByIdProjection(Long id);
	void liberarOrBloquear(Long id, boolean liberar);
	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada, Long... empresasIds);
	Integer findCountTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findTituloModeloAvaliacao(Integer page, Integer pagingSize, Date periodoInicial, Date periodoFinal, Long empresaId, String tituloBusca, Long avaliacaoId, Boolean liberada);
	Collection<AvaliacaoDesempenho> findIdsAvaliacaoDesempenho(Long avaliacaoId);
	Collection<AvaliacaoDesempenho> findComCompetencia(Long empresaId);
}