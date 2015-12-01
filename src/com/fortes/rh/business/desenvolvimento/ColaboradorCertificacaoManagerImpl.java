package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorCertificacaoDao;

public class ColaboradorCertificacaoManagerImpl extends GenericManagerImpl<ColaboradorCertificacao, ColaboradorCertificacaoDao> implements ColaboradorCertificacaoManager
{
	public Collection<ColaboradorCertificacao> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
	}
}
