package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.avaliacao.AvaliacaoDesempenhoManager;
import com.fortes.rh.model.avaliacao.AvaliacaoDesempenho;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.util.CollectionUtil;

public class AvaliacaoDesempenhoDWR
{
	private AvaliacaoDesempenhoManager avaliacaoDesempenhoManager;

	@SuppressWarnings("unchecked")
	public Map<Long, String> getAvaliacoesByEmpresa(Long empresaId)
	{
		Collection<AvaliacaoDesempenho> avaliacoesDesempenho = avaliacaoDesempenhoManager.findAllSelect(empresaId, true, TipoModeloAvaliacao.DESEMPENHO);
		return new CollectionUtil<AvaliacaoDesempenho>().convertCollectionToMap(avaliacoesDesempenho, "getId", ((empresaId == null || empresaId < 0) ? "getTituloComEmpresa" : "getTitulo"));
	}

	public void setAvaliacaoDesempenhoManager(AvaliacaoDesempenhoManager avaliacaoDesempenhoManager) {
		this.avaliacaoDesempenhoManager = avaliacaoDesempenhoManager;
	}
}
