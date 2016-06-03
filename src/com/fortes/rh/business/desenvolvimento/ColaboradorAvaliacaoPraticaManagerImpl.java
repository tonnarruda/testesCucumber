package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

	public Map<Long, Collection<ColaboradorAvaliacaoPratica>> findMapByCertificacaoIdAndColaboradoresIds(Long certificacaoId, Long[] colaboradoresIds) {
		Map<Long, Collection<ColaboradorAvaliacaoPratica>> mapColaboradorAvaliacoesPraticas = new HashMap<Long, Collection<ColaboradorAvaliacaoPratica>>();
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = getDao().findByCertificacaoIdAndColaboradoresIds(certificacaoId, colaboradoresIds);
		
		Long colaboradorId = null;
		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
			colaboradorId = colaboradorAvaliacaoPratica.getColaborador().getId();

			if(!mapColaboradorAvaliacoesPraticas.containsKey(colaboradorId))
				mapColaboradorAvaliacoesPraticas.put(colaboradorId, new ArrayList<ColaboradorAvaliacaoPratica>());
			
			mapColaboradorAvaliacoesPraticas.get(colaboradorId).add(colaboradorAvaliacaoPratica);
		}
		
		return mapColaboradorAvaliacoesPraticas;
	}

	public void setColaboradorCertificacoNull(Long[] colaboradorCertificacoesIds) {
		getDao().setColaboradorCertificacoNull(colaboradorCertificacoesIds);
	}
}
