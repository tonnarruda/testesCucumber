package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.business.sesmt.SolicitacaoEpiManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.web.tags.Option;

@Component
@RemoteProxy(name="SolicitacaoEpiDWR")
public class SolicitacaoEpiDWR {

	@Autowired private SolicitacaoEpiManager solicitacaoEpiManager;
	
	@RemoteMethod
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
}