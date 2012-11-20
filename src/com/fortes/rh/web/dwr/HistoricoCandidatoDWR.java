package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.util.CollectionUtil;

public class HistoricoCandidatoDWR
{
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private HistoricoCandidatoManager historicoCandidatoManager;

	public Map getCandidatoAptoByEtapa(Long etapaId, Long solicitacaoId)
	{
		if(etapaId == -1)
			etapaId = null;

		Collection<CandidatoSolicitacao> candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatoSolicitacaoList(null, null, solicitacaoId, etapaId, null, true, true, true, null, null, null);

		return new CollectionUtil<CandidatoSolicitacao>().convertCollectionToMap(candidatoSolicitacaos, "getId", "getCandidatoNome");
	}
	
	public boolean updateAgenda(Long id, Date data, String horaIni, String horaFim, String observacao)
	{
		try{
			return historicoCandidatoManager.updateAgenda(id, data, horaIni, horaFim, observacao);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager) {
		this.historicoCandidatoManager = historicoCandidatoManager;
	}
}
