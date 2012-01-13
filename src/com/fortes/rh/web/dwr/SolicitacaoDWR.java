package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.util.CollectionUtil;

public class SolicitacaoDWR {

	private SolicitacaoManager solicitacaoManager;

	public Map getSolicitacoes(Long empresaId) 
	{
		Collection<Solicitacao> solicitacaos = solicitacaoManager.findSolicitacaoList(empresaId, false, StatusAprovacaoSolicitacao.APROVADO, false);
		return new CollectionUtil<Solicitacao>().convertCollectionToMap(solicitacaos, "getId", "getDescricaoFormatada");
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager) {
		this.solicitacaoManager = solicitacaoManager;
	}
}
