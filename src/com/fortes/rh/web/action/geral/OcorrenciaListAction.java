package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class OcorrenciaListAction extends MyActionSupportList
{
	private OcorrenciaManager ocorrenciaManager;

	private Collection<Ocorrencia> ocorrencias;

	private Ocorrencia ocorrencia;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception 
	{
		setTotalSize(ocorrenciaManager.getCount(ocorrencia, getEmpresaSistema().getId()));
		ocorrencias = ocorrenciaManager.find(getPage(), getPagingSize(), ocorrencia, getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception 
	{
		try {
			ocorrenciaManager.remove(ocorrencia, getEmpresaSistema());
			addActionSuccess("Ocorrência excluída com sucesso.");
			return Action.SUCCESS;
		} catch (Exception e) {
			if (e.getCause() instanceof IntegraACException){ 
				IntegraACException integraACException = (IntegraACException) e.getCause();
				addActionMessage(integraACException.getMessage().trim());
			}else
				addActionMessage("A ocorreu não pode ser excluída pois possui dependência.");
			e.printStackTrace();
			list();
			return Action.INPUT;
		}
	}

	public Collection<Ocorrencia> getOcorrencias() {
		return ocorrencias;
	}


	public Ocorrencia getOcorrencia(){
		if(ocorrencia == null){
			ocorrencia = new Ocorrencia();
		}
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia){
		this.ocorrencia=ocorrencia;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager){
		this.ocorrenciaManager=ocorrenciaManager;
	}
}