package com.fortes.rh.business.avaliacao;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;

public interface AvaliacaoPraticaManager extends GenericManager<AvaliacaoPratica>
{
	Collection<AvaliacaoPratica> findByCertificacaoId(Long certificacaoId);
	Map<Long, Collection<AvaliacaoPratica>> findMapByCertificacaoId(Long... certificacaoId);
}
