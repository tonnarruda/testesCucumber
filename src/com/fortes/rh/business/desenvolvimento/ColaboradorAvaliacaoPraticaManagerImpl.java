package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorAvaliacaoPraticaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.util.SpringUtil;

public class ColaboradorAvaliacaoPraticaManagerImpl extends GenericManagerImpl<ColaboradorAvaliacaoPratica, ColaboradorAvaliacaoPraticaDao> implements ColaboradorAvaliacaoPraticaManager
{

	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId) {
		return getDao().findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId, colaboradorCertificacaoId);
	}

	public Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId) {
		return getDao().findColaboradorAvaliacaoPraticaQueNaoEstaCertificado(colaboradorId, certificacaoId);
	}

	public void removeColaboradorAvaliacaoPraticaAndColaboradorCertificado(ColaboradorAvaliacaoPratica colaboradorAvaliacaoPraticaRealizada) {
		if(colaboradorAvaliacaoPraticaRealizada != null && colaboradorAvaliacaoPraticaRealizada.getId()!= null){
			ColaboradorCertificacaoManager colaboradorCertificacaoManager = (ColaboradorCertificacaoManager) SpringUtil.getBeanOld("colaboradorCertificacaoManager");
			colaboradorCertificacaoManager.descertificarColaborador(colaboradorAvaliacaoPraticaRealizada.getColaboradorCertificacao().getId(), true);
		}
	}

	public void removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(Long colaboradorCertificacaoId) {
		getDao().removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(colaboradorCertificacaoId);
	}
}
