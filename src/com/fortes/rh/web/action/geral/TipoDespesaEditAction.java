package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class TipoDespesaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private TipoDespesaManager tipoDespesaManager;
	private TipoDespesa tipoDespesa;
	private Collection<TipoDespesa> tipoDespesas;

	private void prepare() throws Exception
	{
		if(tipoDespesa != null && tipoDespesa.getId() != null)
			tipoDespesa = (TipoDespesa) tipoDespesaManager.findById(tipoDespesa.getId());

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
		tipoDespesa.setEmpresa(getEmpresaSistema());
		tipoDespesaManager.save(tipoDespesa);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		tipoDespesaManager.update(tipoDespesa);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		tipoDespesas = tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			tipoDespesaManager.remove(tipoDespesa.getId());
			addActionSuccess("Tipo de Despesa excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este Tipo de Despesa.");
		}

		return list();
	}
	
	public TipoDespesa getTipoDespesa()
	{
		if(tipoDespesa == null)
			tipoDespesa = new TipoDespesa();
		return tipoDespesa;
	}

	public void setTipoDespesa(TipoDespesa tipoDespesa)
	{
		this.tipoDespesa = tipoDespesa;
	}

	public void setTipoDespesaManager(TipoDespesaManager tipoDespesaManager)
	{
		this.tipoDespesaManager = tipoDespesaManager;
	}
	
	public Collection<TipoDespesa> getTipoDespesas()
	{
		return tipoDespesas;
	}
}
