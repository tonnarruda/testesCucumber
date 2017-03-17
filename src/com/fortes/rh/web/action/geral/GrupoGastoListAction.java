package com.fortes.rh.web.action.geral;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.GrupoGastoManager;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class GrupoGastoListAction extends MyActionSupportList
{
	@Autowired private GrupoGastoManager grupoGastoManager;

	private Collection<GrupoGasto> grupoGastos;

	private GrupoGasto grupoGasto;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String list() throws Exception {
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(grupoGastoManager.getCount(keys, values));
		grupoGastos = grupoGastoManager.find(getPage(), getPagingSize(), keys, values, orders);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			grupoGastoManager.remove(new Long[]{grupoGasto.getId()});
			addActionMessage("Grupo excluído com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Não foi possível excluir este Grupo.");
			e.printStackTrace();
		}


		return list();
	}

	public Collection<GrupoGasto> getGrupoGastos() {
		return grupoGastos;
	}


	public GrupoGasto getGrupoGasto(){
		if(grupoGasto == null){
			grupoGasto = new GrupoGasto();
		}
		return grupoGasto;
	}

	public void setGrupoGasto(GrupoGasto grupoGasto){
		this.grupoGasto=grupoGasto;
	}
}