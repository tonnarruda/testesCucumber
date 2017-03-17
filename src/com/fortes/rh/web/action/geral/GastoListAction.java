package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.GastoManager;
import com.fortes.rh.model.geral.Gasto;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class GastoListAction extends MyActionSupportList
{
	@Autowired private GastoManager gastoManager;

	private Collection<Gasto> gastos;

	private Gasto gasto = new Gasto();

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(gastoManager.getCount(keys, values));

		if (getTotalSize() < getPagingSize() || getTotalSize() == getPagingSize())
			gastos = gastoManager.find(keys, values, orders);
		else
			gastos = gastoManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			gastoManager.remove(new Long[]{gasto.getId()});
			addActionMessage("Investimento excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir esta Investimento.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<Gasto> getGastos()
	{
		return gastos;
	}

	public Gasto getGasto()
	{
		return gasto;
	}

	public void setGasto(Gasto gasto)
	{
		this.gasto = gasto;
	}
}