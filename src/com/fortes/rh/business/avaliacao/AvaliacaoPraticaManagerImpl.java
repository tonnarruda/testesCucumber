package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;

public class AvaliacaoPraticaManagerImpl extends GenericManagerImpl<AvaliacaoPratica, AvaliacaoPraticaDao> implements AvaliacaoPraticaManager
{
	public Collection<AvaliacaoPratica> findByCertificacaoId(Long certificacaoId) {
		return getDao().findByCertificacaoId(certificacaoId);
	}
}
