package com.fortes.rh.web.action.sesmt;

import java.util.Collection;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class EpcListAction extends MyActionSupportList
{
	private EpcManager epcManager;

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
			addActionWarning("O EPC solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
		}
		else
		{
			try
			{
				epcManager.remove(new Long[]{epc.getId()});
				addActionSuccess("EPC excluído com sucesso.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este EPC.");
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

	public void setEpcManager(EpcManager epcManager){
		this.epcManager=epcManager;
	}
}