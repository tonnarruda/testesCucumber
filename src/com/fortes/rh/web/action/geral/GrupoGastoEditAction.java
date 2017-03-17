package com.fortes.rh.web.action.geral;


import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.geral.GrupoGastoManager;
import com.fortes.rh.model.geral.GrupoGasto;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class GrupoGastoEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private GrupoGastoManager grupoGastoManager;

	private GrupoGasto grupoGasto;

	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(grupoGasto != null && grupoGasto.getId() != null) {
			grupoGasto = (GrupoGasto) grupoGastoManager.findById(grupoGasto.getId());
		}

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
		grupoGasto.setEmpresa(getEmpresaSistema());

		grupoGastoManager.save(grupoGasto);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		grupoGasto.setEmpresa(getEmpresaSistema());

		grupoGastoManager.update(grupoGasto);

		return Action.SUCCESS;
	}

	public GrupoGasto getGrupoGasto()
	{
		if(grupoGasto == null)
		{
			grupoGasto = new GrupoGasto();
		}
		return grupoGasto;
	}

	public void setGrupoGasto(GrupoGasto grupoGasto)
	{
		this.grupoGasto=grupoGasto;
	}

	public Object getModel()
	{
		return getGrupoGasto();
	}
}