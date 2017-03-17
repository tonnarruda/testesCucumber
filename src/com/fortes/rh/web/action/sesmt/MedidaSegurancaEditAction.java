package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.MedidaSegurancaManager;
import com.fortes.rh.model.sesmt.MedidaSeguranca;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class MedidaSegurancaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private MedidaSegurancaManager medidaSegurancaManager;
	private MedidaSeguranca medidaSeguranca;
	private Collection<MedidaSeguranca> medidasSeguranca;
	private String descricao;

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
		try {
			medidaSeguranca.setEmpresa(getEmpresaSistema());
			medidaSegurancaManager.save(medidaSeguranca);
			
			addActionSuccess("Medida de segurança cadastrada com sucesso.");
		}
		catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a medida de segurança.");
			
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			medidaSegurancaManager.update(medidaSeguranca);
			addActionSuccess("Medida de segurança atualizada com sucesso.");
		}
		catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a medida de segurança.");
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		medidasSeguranca = medidaSegurancaManager.findAllSelect(descricao, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			medidaSegurancaManager.remove(medidaSeguranca.getId());
			addActionSuccess("Medida de segurança excluída com sucesso.");
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

	public Collection<MedidaSeguranca> getMedidasSeguranca() {
		return medidasSeguranca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}