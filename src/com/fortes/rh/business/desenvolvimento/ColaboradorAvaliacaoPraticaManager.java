package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public interface ColaboradorAvaliacaoPraticaManager extends GenericManager<ColaboradorAvaliacaoPratica>
{
	Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId);
	void removeAllByColaboradorId(Long colaboradorId);
	Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId);
	void removeColaboradorAvaliacaoPraticaAndColaboradorCertificado(ColaboradorAvaliacaoPratica colaboradorAvaliacaoPraticaRealizada);
	void removeColaboradorAvaliacaoPraticaByColaboradorCertificacaoId(Long colaboradorTurmaId);
	
}
