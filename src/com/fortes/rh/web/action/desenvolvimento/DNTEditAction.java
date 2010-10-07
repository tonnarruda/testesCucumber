package com.fortes.rh.web.action.desenvolvimento;

import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class DNTEditAction extends MyActionSupportEdit implements ModelDriven
{
	private DNTManager DNTManager;

	private DNT dnt;
	private ColaboradorTurma colaboradorTurma;
	private AreaOrganizacional areaFiltro;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(dnt != null && dnt.getId() != null)
			dnt = (DNT) DNTManager.findById(dnt.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if (dnt.getEmpresa().getId().equals(getEmpresaSistema().getId()))
			return Action.SUCCESS;
		else
		{
			addActionError("Você não tem acesso a DNTs de outra empresa!");
			return Action.ERROR;
		}
	}

	public String insert() throws Exception
	{
		dnt.setEmpresa(getEmpresaSistema());
		DNTManager.save(dnt);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		dnt.setEmpresa(getEmpresaSistema());
		DNTManager.update(dnt);
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getDnt();
	}

	public DNT getDnt()
	{
		if(dnt == null)
			dnt = new DNT();
		return dnt;
	}

	public void setDnt(DNT dnt)
	{
		this.dnt = dnt;
	}

	public void setDNTManager(DNTManager DNTManager)
	{
		this.DNTManager = DNTManager;
	}

	public ColaboradorTurma getColaboradorTurma()
	{
		return colaboradorTurma;
	}

	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma)
	{
		this.colaboradorTurma = colaboradorTurma;
	}

	public AreaOrganizacional getAreaFiltro()
	{
		return areaFiltro;
	}

	public void setAreaFiltro(AreaOrganizacional areaFiltro)
	{
		this.areaFiltro = areaFiltro;
	}
}