package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.AvaliacaoPraticaDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;

public class AvaliacaoPraticaManagerImpl extends GenericManagerImpl<AvaliacaoPratica, AvaliacaoPraticaDao> implements AvaliacaoPraticaManager
{
	public Collection<AvaliacaoPratica> findByCertificacaoId(Long certificacaoId) {
		return getDao().findByCertificacaoId(certificacaoId);
	}
	
	public Map<Long, Collection<AvaliacaoPratica>> findMapByCertificacaoId(Long... certificacoesId) {
		Map<Long, Collection<AvaliacaoPratica>> mapAvaliacoesPraticas = new HashMap<Long, Collection<AvaliacaoPratica>>(); 
		Collection<AvaliacaoPratica> avaliacoesPraticas = getDao().findByCertificacaoId(certificacoesId);

		Long certificacaoId = null;
		for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas) {
			
			certificacaoId = avaliacaoPratica.getCertificacaoId();
			if(!mapAvaliacoesPraticas.containsKey(certificacaoId))
				mapAvaliacoesPraticas.put(certificacaoId, new ArrayList<AvaliacaoPratica>());
			
			mapAvaliacoesPraticas.get(certificacaoId).add(avaliacaoPratica);
		}
		
		return mapAvaliacoesPraticas;
	}
}
