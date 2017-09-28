package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.SolicitacaoEpiItemManager;
import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.util.DateUtil;
import com.fortes.web.tags.CheckBox;

public class SolicitacaoEpiDWR {

	private SolicitacaoEpiManager solicitacaoEpiManager;
	private SolicitacaoEpiItemManager solicitacaoEpiItemManager;
	
	public Collection<CheckBox> getByColaboradorId(Long colaboradorId)
	{
		Collection<CheckBox> solicitacoesEpiCheck = new ArrayList<CheckBox>();
		Collection<SolicitacaoEpi> solicitacoesEpi = solicitacaoEpiManager.findByColaboradorId(colaboradorId); 
		CheckBox check = null;

		for (SolicitacaoEpi solicitacaoEpi : solicitacoesEpi) {
			
			check = new CheckBox();
			check.setId(solicitacaoEpi.getId());
			check.setNome(solicitacaoEpi.getDataFormatada());
			
			solicitacoesEpiCheck.add(check);
		}

		return solicitacoesEpiCheck;
	}

	public String validaDataDevolucao(String data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId, Integer qtdASerDevolvida, Long solicitacaoEpiId){
		if(data.trim().length() != 10 || solicitacaoEpiItemId == null)
			return null;
		
		if(qtdASerDevolvida == null)
			qtdASerDevolvida = 0;
		
		SolicitacaoEpi solicitacaoEpi = solicitacaoEpiManager.findEntidadeComAtributosSimplesById(solicitacaoEpiId);
		return solicitacaoEpiItemManager.validaDataDevolucao(DateUtil.criarDataDiaMesAno(data), solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId, qtdASerDevolvida, solicitacaoEpi.getData());
	}
	
	public void setSolicitacaoEpiManager(SolicitacaoEpiManager solicitacaoEpiManager) {
		this.solicitacaoEpiManager = solicitacaoEpiManager;
	}

	public void setSolicitacaoEpiItemManager(SolicitacaoEpiItemManager solicitacaoEpiItemManager) {
		this.solicitacaoEpiItemManager = solicitacaoEpiItemManager;
	}
}