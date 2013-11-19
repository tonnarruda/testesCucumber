package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.MedidaSegurancaManager;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class MedidaSegurancaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private MedidaSegurancaManager medidaSegurancaManager;
	
	private MedidaSeguranca medidaSeguranca;
	
	private Collection<MedidaSeguranca> medidasSeguranca;

	private void prepare() throws Exception
	{
		if(medidaSeguranca != null && medidaSeguranca.getId() != null)
			medidaSeguranca = (MedidaSeguranca) medidaSegurancaManager.findById(medidaSeguranca.getId());

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
		medidaSegurancaManager.save(medidaSeguranca);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		medidaSegurancaManager.update(medidaSeguranca);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		medidasSeguranca = medidaSegurancaManager.findAllSelect(null, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			medidaSegurancaManager.remove(medidaSeguranca.getId());
			addActionMessage("Medida de segurança excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta medida de segurança.");
		}

		return list();
	}
	
	public MedidaSeguranca getMedidaSeguranca()
	{
		if(medidaSeguranca == null)
			medidaSeguranca = new MedidaSeguranca();
		return medidaSeguranca;
	}

	public void setMedidaSeguranca(MedidaSeguranca medidaSeguranca)
	{
		this.medidaSeguranca = medidaSeguranca;
	}

	public void setMedidaSegurancaManager(MedidaSegurancaManager medidaSegurancaManager)
	{
		this.medidaSegurancaManager = medidaSegurancaManager;
	}

	public Collection<MedidaSeguranca> getMedidasSeguranca() {
		return medidasSeguranca;
	}
}
