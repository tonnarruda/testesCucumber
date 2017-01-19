package com.fortes.rh.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.business.geral.GastoEmpresaItemManager;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial"})
public class GastoEmpresaItemListAction extends MyActionSupportList
{
	private GastoEmpresaItemManager gastoEmpresaItemManager;

	private Collection<GastoEmpresaItem> gastoEmpresaItems;

	private GastoEmpresaItem gastoEmpresaItem;
	private GastoEmpresa gastoEmpresa;

	private String msgAlert = "";

	public GastoEmpresa getGastoEmpresa()
	{
		return gastoEmpresa;
	}

	public void setGastoEmpresa(GastoEmpresa gastoEmpresa)
	{
		this.gastoEmpresa = gastoEmpresa;
	}

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

        Map session = ActionContext.getContext().getSession();
        gastoEmpresaItems = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

        if (gastoEmpresa != null && gastoEmpresa.getId() != null && gastoEmpresaItems == null)
        	gastoEmpresaItems = gastoEmpresaItemManager.find(new String[]{"gastoEmpresa.id"}, new Object[]{gastoEmpresa.getId()});

        if (gastoEmpresaItems == null)
        	gastoEmpresaItems = new ArrayList<GastoEmpresaItem>();

        session.put("SESSION_GASTO_ITENS",gastoEmpresaItems);

        if(!msgAlert.equals(""))
        	addActionMessage(msgAlert);

        return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String delete() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

		Map session = ActionContext.getContext().getSession();
		Collection<GastoEmpresaItem> lista = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

		for (GastoEmpresaItem e : lista)
		{
			if (e.getId().equals(gastoEmpresaItem.getId()))
			{
				lista.remove(e);
				break;
			}
		}

		session.put("SESSION_GASTO_ITENS", lista);

		addActionMessage("Item excluído com sucesso");

		return list();
	}

	public Collection<GastoEmpresaItem> getGastoEmpresaItems() {
		return gastoEmpresaItems;
	}


	public GastoEmpresaItem getGastoEmpresaItem(){
		if(gastoEmpresaItem == null){
			gastoEmpresaItem = new GastoEmpresaItem();
		}
		return gastoEmpresaItem;
	}

	public void setGastoEmpresaItem(GastoEmpresaItem gastoEmpresaItem){
		this.gastoEmpresaItem=gastoEmpresaItem;
	}

	public void setGastoEmpresaItemManager(GastoEmpresaItemManager gastoEmpresaItemManager){
		this.gastoEmpresaItemManager=gastoEmpresaItemManager;
	}
}