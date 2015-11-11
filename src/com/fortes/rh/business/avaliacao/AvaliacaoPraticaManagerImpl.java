package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;

public class AvaliacaoPraticaManagerImpl extends GenericManagerImpl<AvaliacaoPratica, AvaliacaoPraticaDao> implements AvaliacaoPraticaManager
{
	public Collection<AvaliacaoPratica> findByCertificacaoId(Long certificacaoId) {
		return getDao().findByCertificacaoId(certificacaoId);
	}
}
