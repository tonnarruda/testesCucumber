package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.web.tags.Option;

public class SolicitacaoEpiDWR {

	private SolicitacaoEpiManager solicitacaoEpiManager;
	
	public Collection<Option> getByColaboradorId(Long colaboradorId)
	{
		Collection<Option> options = new ArrayList<Option>();
		Collection<SolicitacaoEpi> solicitacoesEpi = solicitacaoEpiManager.findByColaboradorId(colaboradorId); 
		Option option = null;

		for (SolicitacaoEpi solicitacaoEpi : solicitacoesEpi) {
			
			option = new Option();
			option.setId(solicitacaoEpi.getId());
			option.setNome(solicitacaoEpi.getDataFormatada());
			
			options.add(option);
		}

		return options;
	}

	public void setSolicitacaoEpiManager(SolicitacaoEpiManager solicitacaoEpiManager) {
		this.solicitacaoEpiManager = solicitacaoEpiManager;
	}

}