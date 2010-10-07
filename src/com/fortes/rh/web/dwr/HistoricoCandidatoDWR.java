package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.util.CollectionUtil;

public class HistoricoCandidatoDWR
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	public Map getCandidatoAptoByEtapa(Long etapaId, Long solicitacaoId)
	{
		if(etapaId == -1)
			etapaId = null;

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacaoId, etapaId, null, true, true, true, null, null);

		return new CollectionUtil<CandidatoSolicitacao>().convertCollectionToMap(candidatoSolicitacaos, "getId", "getCandidatoNome");
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}
}
