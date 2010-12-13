package com.fortes.rh.web.action.captacao;


import java.util.Collection;

import com.fortes.rh.business.captacao.AtitudeManager;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class AtitudeEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private AtitudeManager atitudeManager;
	private Atitude atitude;
	private Collection<Atitude> atitudes;

	private void prepare() throws Exception
	{
		if(atitude != null && atitude.getId() != null)
			atitude = (Atitude) atitudeManager.findById(atitude.getId());

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
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.save(atitude);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		atitude.setEmpresa(getEmpresaSistema());
		atitudeManager.update(atitude);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		String[] properties = new String[]{"id","nome"};
		String[] sets = new String[]{"id","nome"};
		String[] keys = new String[]{"empresa.id"};
		Object[] values = new Object[]{getEmpresaSistema().getId()};
		String[] orders = new String[]{"nome"};

		setTotalSize(atitudeManager.getCount(keys, values));
		atitudes = atitudeManager.findToList(properties, sets, keys, values,getPage(), getPagingSize(), orders);

		return Action.SUCCESS;

	}

	public String delete() throws Exception
	{
		try
		{
			atitudeManager.remove(atitude.getId());
			addActionMessage("Atitude excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta atitude.");
		}

		return Action.SUCCESS;
	}
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public Atitude getAtitude()
	{
		if(atitude == null)
			atitude = new Atitude();
		return atitude;
	}

	public void setAtitude(Atitude atitude)
	{
		this.atitude = atitude;
	}

	public void setAtitudeManager(AtitudeManager atitudeManager)
	{
		this.atitudeManager = atitudeManager;
	}
	
	public Collection<Atitude> getAtitudes()
	{
		return atitudes;
	}
}
