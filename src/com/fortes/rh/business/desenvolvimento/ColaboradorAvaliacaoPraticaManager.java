package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;

public interface ColaboradorAvaliacaoPraticaManager extends GenericManager<ColaboradorAvaliacaoPratica>
{
	Collection<ColaboradorAvaliacaoPratica> findByColaboradorIdAndCertificacaoId(Long colaboradorId, Long certificacaoId, Long colaboradorCertificacaoId, Long avaliacaoPraticaId, Boolean ordenarPorDataAscOuDesc, Boolean colabCertificacaoIsNull);
	void removeByColaboradorCertificacaoId(Long colaboradorCertificacaoId);
	Map<Long, Collection<ColaboradorAvaliacaoPratica>> findMapByCertificacaoIdAndColaboradoresIds(Long certificacaoId, Long[] colaboradoresIds);
}
