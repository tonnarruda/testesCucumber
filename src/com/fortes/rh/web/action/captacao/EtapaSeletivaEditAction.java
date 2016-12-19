package com.fortes.rh.web.action.captacao;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class EtapaSeletivaEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private EtapaSeletivaManager etapaSeletivaManager;
	private EtapaSeletiva etapaSeletiva;

	public Object getModel()
	{
		return getEtapaSeletiva();
	}

	private void prepare() throws Exception
	{
		if(etapaSeletiva != null && etapaSeletiva.getId() != null)
		{
			etapaSeletiva = (EtapaSeletiva) etapaSeletivaManager.findByEtapaSeletivaId(etapaSeletiva.getId(),getEmpresaSistema().getId());
		}
	}

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		etapaSeletiva.setOrdem(etapaSeletivaManager.sugerirOrdem(getEmpresaSistema().getId()));
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		etapaSeletiva.setEmpresa(getEmpresaSistema());
		etapaSeletivaManager.save(etapaSeletiva);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		etapaSeletivaManager.update(etapaSeletiva, getEmpresaSistema());

		return Action.SUCCESS;
	}

	public EtapaSeletiva getEtapaSeletiva()
	{
		if(etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();

		return etapaSeletiva;
	}

	public void setEtapaSeletiva(EtapaSeletiva etapaSeletiva)
	{
		this.etapaSeletiva=etapaSeletiva;
	}
}