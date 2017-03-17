package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.GastoEmpresaItemManager;
import com.fortes.rh.business.geral.GastoEmpresaManager;
import com.fortes.rh.model.geral.GastoEmpresa;
import com.fortes.rh.model.geral.GastoEmpresaItem;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class GastoEmpresaListAction extends MyActionSupportList
{
	@Autowired private GastoEmpresaManager gastoEmpresaManager;
	@Autowired private GastoEmpresaItemManager gastoEmpresaItemManager;
	private Collection<GastoEmpresa> gastoEmpresas;

	private GastoEmpresa gastoEmpresa;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception {

		setTotalSize(gastoEmpresaManager.getCount(getEmpresaSistema().getId()));
		gastoEmpresas = gastoEmpresaManager.findAllList(getPage(), getPagingSize(), getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			Collection<GastoEmpresaItem> itens = gastoEmpresaItemManager.find(new String[]{"gastoEmpresa.id"}, new Object[]{gastoEmpresa.getId()});
			gastoEmpresaItemManager.remove((new CollectionUtil<GastoEmpresaItem>()).convertCollectionToArrayIds(itens));
			gastoEmpresaManager.remove(new Long[]{gastoEmpresa.getId()});

			addActionMessage("Investimento da Empresa excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Investimento da Empresa.");
			e.printStackTrace();
		}

		return list();
	}

	public Collection getGastoEmpresas() {
		return gastoEmpresas;
	}

	public GastoEmpresa getGastoEmpresa(){
		if(gastoEmpresa == null){
			gastoEmpresa = new GastoEmpresa();
		}
		return gastoEmpresa;
	}

	public void setGastoEmpresa(GastoEmpresa gastoEmpresa){
		this.gastoEmpresa=gastoEmpresa;
	}
}