package com.fortes.rh.web.action.geral;

import java.util.Collection;

import com.fortes.rh.business.geral.OcorrenciaManager;
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

	public String list() throws Exception {
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"descricao asc"};

		setTotalSize(ocorrenciaManager.getCount(keys, values));
		ocorrencias = ocorrenciaManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception 
	{
		ocorrenciaManager.remove(ocorrencia, getEmpresaSistema());
		addActionMessage("Ocorrência excluída com sucesso.");

		return Action.SUCCESS;
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