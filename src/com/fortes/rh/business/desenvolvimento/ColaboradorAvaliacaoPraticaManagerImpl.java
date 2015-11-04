package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;

public class ColaboradorAvaliacaoPraticaManagerImpl extends GenericManagerImpl<ColaboradorAvaliacaoPratica, ColaboradorAvaliacaoPraticaDao> implements ColaboradorAvaliacaoPraticaManager
{
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}
}
