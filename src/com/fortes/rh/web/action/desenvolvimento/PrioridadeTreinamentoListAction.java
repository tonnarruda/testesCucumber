package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class PrioridadeTreinamentoListAction extends MyActionSupportList
{
	@Autowired private PrioridadeTreinamentoManager prioridadeTreinamentoManager;
	private Collection<PrioridadeTreinamento> prioridadeTreinamentos;
	private PrioridadeTreinamento prioridadeTreinamento;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		setTotalSize(prioridadeTreinamentoManager.getCount());
		prioridadeTreinamentos = prioridadeTreinamentoManager.find(getPage(), getPagingSize(), new String[]{"descricao"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			prioridadeTreinamentoManager.remove(prioridadeTreinamento.getId());
			addActionMessage("Prioridade de Treinamento excluída com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir esta Prioridade de Treinamento.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection<PrioridadeTreinamento> getPrioridadeTreinamentos()
	{
		return prioridadeTreinamentos;
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
}