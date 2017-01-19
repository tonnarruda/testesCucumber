package com.fortes.rh.web.action.geral;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.business.geral.GastoManager;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;
import com.opensymphony.xwork.Preparable;

@SuppressWarnings({"serial"})
public class GastoEmpresaItemEditAction extends MyActionSupportEdit implements ModelDriven,Preparable
{
	private GastoManager gastoManager;

	private GastoEmpresaItem gastoEmpresaItem;

	private Collection<Gasto> gastos;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public void prepare() throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();

        response.addHeader("Expires", "0");
        response.addHeader("Pragma", "no-cache, no-store");
        response.addHeader("Content-Transfer-Encoding", "binary");
        response.addHeader("Cache-Control", "no-cache, no-store");

		this.gastos = gastoManager.findByEmpresa(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		//prepare();
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String prepareUpdate() throws Exception
	{
		//prepare();

		Map session = ActionContext.getContext().getSession();
		Collection<GastoEmpresaItem> lista = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

		for (GastoEmpresaItem e : lista)
		{
			if (e.getId().equals(gastoEmpresaItem.getId()))
			{
				gastoEmpresaItem = e;
				break;
			}
		}

		return Action.SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public String insert() throws Exception
	{
		Map session = ActionContext.getContext().getSession();
		Collection<GastoEmpresaItem> lista = (Collection<GastoEmpresaItem>) session.get("SESSION_GASTO_ITENS");

		Integer id =  (lista.size() + 1);
		gastoEmpresaItem.setId(Long.valueOf(id.toString()));
		gastoEmpresaItem.setGasto(gastoManager.findByIdProjection(gastoEmpresaItem.getGasto().getId()));

		lista.add(gastoEmpresaItem);
		session.put("SESSION_GASTO_ITENS", lista);

		addActionMessage("Item incluído com sucesso!");

		return prepareInsert();
	}

	@SuppressWarnings("unchecked")
	public String update() throws Exception
	{
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

		gastoEmpresaItem.setGasto(gastoManager.findByIdProjection(gastoEmpresaItem.getGasto().getId()));

		lista.add(gastoEmpresaItem);
		session.put("SESSION_GASTO_ITENS", lista);

		msgAlert = "Item atualizado com sucesso!";

		return Action.SUCCESS;

	}

	public GastoEmpresaItem getGastoEmpresaItem()
	{
		if(gastoEmpresaItem == null)
			gastoEmpresaItem = new GastoEmpresaItem();
		return gastoEmpresaItem;
	}

	public void setGastoEmpresaItem(GastoEmpresaItem gastoEmpresaItem)
	{
		this.gastoEmpresaItem=gastoEmpresaItem;
	}

	public Object getModel()
	{
		return getGastoEmpresaItem();
	}

	public void setGastoManager(GastoManager gastoManager)
	{
		this.gastoManager = gastoManager;
	}
	public Collection<Gasto> getGastos()
	{
		return gastos;
	}
	public void setGastos(Collection<Gasto> gastos)
	{
		this.gastos = gastos;
	}
}