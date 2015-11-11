package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;

public interface AvaliacaoPraticaDao extends GenericDao<AvaliacaoPratica> 
{
	Collection<AvaliacaoPratica> findByCertificacaoId(Long certificacaoId);
}
