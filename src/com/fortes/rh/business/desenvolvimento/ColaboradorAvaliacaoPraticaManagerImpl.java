package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaManagerImpl extends GenericManagerImpl<ColaboradorAvaliacaoPratica, ColaboradorAvaliacaoPraticaDao> implements ColaboradorAvaliacaoPraticaManager
{
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId, colaboradorCertificacaoId);
	}

	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findColaboradorAvaliacaoPraticaQueNaoEstaCertificado(colaboradorId, certificacaoId);
	}

	public void removeByColaboradorCertificacaoId(Long colaboradorCertificacaoId) {
		getDao().removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(colaboradorCertificacaoId);
	}
}
