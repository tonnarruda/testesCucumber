package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AfastamentoEditAction extends MyActionSupportList
{
	private AfastamentoManager afastamentoManager;
	private Afastamento afastamento;
	private Collection<Afastamento> afastamentos;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepare() throws Exception
	{
		if(afastamento != null && afastamento.getId() != null)
			afastamento = (Afastamento) afastamentoManager.findById(afastamento.getId());

		return SUCCESS;
	}

	public String insert() throws Exception
	{
		afastamentoManager.save(afastamento);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		afastamentoManager.update(afastamento);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		afastamentos = afastamentoManager.findAll(new String[]{"descricao"});

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			afastamentoManager.remove(afastamento.getId());
			addActionSuccess("Afastamento excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Não foi possível excluir este afastamento.");
		}

		return list();
	}

	public Afastamento getAfastamento()
	{
		if(afastamento == null)
			afastamento = new Afastamento();
		return afastamento;
	}

	public void setAfastamento(Afastamento afastamento)
	{
		this.afastamento = afastamento;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}

	public Collection<Afastamento> getAfastamentos()
	{
		return afastamentos;
	}
}