package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.ComposicaoSesmtManager;
import com.fortes.rh.model.sesmt.ComposicaoSesmt;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ComposicaoSesmtEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private ComposicaoSesmtManager composicaoSesmtManager;
	private ComposicaoSesmt composicaoSesmt;
	private Collection<ComposicaoSesmt> composicaoSesmts;

	private void prepare() throws Exception
	{
		if(composicaoSesmt != null && composicaoSesmt.getId() != null)
			composicaoSesmt = (ComposicaoSesmt) composicaoSesmtManager.findByIdProjection(composicaoSesmt.getId());

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
		composicaoSesmt.setEmpresa(getEmpresaSistema());
		composicaoSesmtManager.save(composicaoSesmt);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		composicaoSesmtManager.update(composicaoSesmt);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		composicaoSesmts = composicaoSesmtManager.findAllSelect(getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			composicaoSesmtManager.remove(composicaoSesmt.getId());
			addActionMessage("Composição do SESMT excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta Composição do SESMT.");
		}

		return list();
	}
	
	public ComposicaoSesmt getComposicaoSesmt()
	{
		if(composicaoSesmt == null)
			composicaoSesmt = new ComposicaoSesmt();
		return composicaoSesmt;
	}

	public void setComposicaoSesmt(ComposicaoSesmt composicaoSesmt)
	{
		this.composicaoSesmt = composicaoSesmt;
	}
	
	public Collection<ComposicaoSesmt> getComposicaoSesmts()
	{
		return composicaoSesmts;
	}
}