package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public class ColaboradorAvaliacaoPraticaManagerImpl extends GenericManagerImpl<ColaboradorAvaliacaoPratica, ColaboradorAvaliacaoPraticaDao> implements ColaboradorAvaliacaoPraticaManager
{
	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId, Long avaliacaoPraticaId, Boolean ordenarPorDataAscOuDesc, Boolean colabCertificacaoIsNull) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId, colaboradorCertificacaoId, avaliacaoPraticaId, ordenarPorDataAscOuDesc, colabCertificacaoIsNull);
	}

	public void removeByColaboradorCertificacaoId(Long colaboradorCertificacaoId) {
		getDao().removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(colaboradorCertificacaoId);
	}
}
