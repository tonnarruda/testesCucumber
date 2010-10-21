package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;

public interface AvaliacaoDesempenhoDao extends GenericDao<AvaliacaoDesempenho> {
	Collection<AvaliacaoDesempenho> findAllSelect(Long empresaId, Boolean ativa);

	public AvaliacaoDesempenho findByIdProjection(Long id);

	void liberarOrBloquear(Long id, boolean liberar);

	Collection<AvaliacaoDesempenho> findByAvaliador(Long avaliadorId, Boolean liberada);
}