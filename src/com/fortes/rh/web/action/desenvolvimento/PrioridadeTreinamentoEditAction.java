package com.fortes.rh.web.action.desenvolvimento;

import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class PrioridadeTreinamentoEditAction extends ActionSupport implements ModelDriven
{
	private PrioridadeTreinamentoManager prioridadeTreinamentoManager;

	private PrioridadeTreinamento prioridadeTreinamento;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public void prepare() throws Exception
	{
		if (prioridadeTreinamento != null && prioridadeTreinamento.getId() != null)
			prioridadeTreinamento = (PrioridadeTreinamento) prioridadeTreinamentoManager.findById(prioridadeTreinamento.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		prioridadeTreinamentoManager.save(prioridadeTreinamento);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		prioridadeTreinamentoManager.update(prioridadeTreinamento);

		return Action.SUCCESS;
	}

	public PrioridadeTreinamento getPrioridadeTreinamento()
	{
		if (prioridadeTreinamento == null)
			prioridadeTreinamento = new PrioridadeTreinamento();

		return prioridadeTreinamento;
	}

	public void setPrioridadeTreinamento(PrioridadeTreinamento prioridadeTreinamento)
	{
		this.prioridadeTreinamento = prioridadeTreinamento;
	}

	public Object getModel()
	{
		return getPrioridadeTreinamento();
	}

	public void setPrioridadeTreinamentoManager(PrioridadeTreinamentoManager prioridadeTreinamentoManager)
	{
		this.prioridadeTreinamentoManager = prioridadeTreinamentoManager;
	}

}