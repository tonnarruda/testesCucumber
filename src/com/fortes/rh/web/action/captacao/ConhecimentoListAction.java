package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ConhecimentoListAction extends MyActionSupportList
{
	private ConhecimentoManager conhecimentoManager;
	private Collection<Conhecimento> conhecimentos;
	private Conhecimento conhecimento;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome"};
		String[] sets = new String[]{"id","nome"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(conhecimentoManager.getCount(keys, values));
		conhecimentos = conhecimentoManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		conhecimentoManager.remove(conhecimento.getId());
		addActionMessage("Conhecimento excluído com sucesso.");

		return Action.SUCCESS;
	}

	public Collection<Conhecimento> getConhecimentos() {
		return conhecimentos;
	}


	public Conhecimento getConhecimento(){
		if(conhecimento == null){
			conhecimento = new Conhecimento();
		}
		return conhecimento;
	}

	public void setConhecimento(Conhecimento conhecimento){
		this.conhecimento=conhecimento;
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager){
		this.conhecimentoManager=conhecimentoManager;
	}
}