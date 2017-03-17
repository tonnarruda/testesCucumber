package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EpcListAction extends MyActionSupportList
{
	@Autowired private EpcManager epcManager;

	private Collection<Epc> epcs;

	private Epc epc;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","codigo","descricao"};
		String[] sets = new String[]{"id","codigo","descricao"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"descricao"};

		setTotalSize(epcManager.getCount(keys, values));
		epcs = epcManager.findToList(properties, sets, keys, values, getPage(), getPagingSize(), orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		if(epc.getId() == null)
			return SUCCESS;

		Epc epcTmp = epcManager.findByIdProjection(epc.getId());

		if(epcTmp == null || !getEmpresaSistema().equals(epcTmp.getEmpresa()))
		{
			addActionError("O EPC solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}
		else
		{
			try
			{
				epcManager.remove(new Long[]{epc.getId()});
				addActionMessage("EPC excluído com sucesso.");
			}
			catch (Exception e)
			{
				addActionError("Não foi possível excluir este EPC.");
				e.printStackTrace();
			}
		}

		return list();
	}

	public Collection getEpcs() {
		return epcs;
	}


	public Epc getEpc(){
		if(epc == null){
			epc = new Epc();
		}
		return epc;
	}

	public void setEpc(Epc epc){
		this.epc=epc;
	}
}