package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.NaturezaLesaoManager;
import com.fortes.rh.model.sesmt.NaturezaLesao;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class NaturezaLesaoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private NaturezaLesaoManager naturezaLesaoManager;
	private NaturezaLesao naturezaLesao;
	private Collection<NaturezaLesao> naturezaLesaos;

	private void prepare() throws Exception
	{
		if(naturezaLesao != null && naturezaLesao.getId() != null)
			naturezaLesao = (NaturezaLesao) naturezaLesaoManager.findById(naturezaLesao.getId());

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
		naturezaLesao.setEmpresa(getEmpresaSistema());
		naturezaLesaoManager.save(naturezaLesao);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		naturezaLesao.setEmpresa(getEmpresaSistema());
		naturezaLesaoManager.update(naturezaLesao);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		naturezaLesaos = naturezaLesaoManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			naturezaLesaoManager.remove(naturezaLesao.getId());
			addActionMessage("Natureza da Lesão excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta Natureza da Lesão.");
		}

		return list();
	}
	
	public NaturezaLesao getNaturezaLesao()
	{
		if(naturezaLesao == null)
			naturezaLesao = new NaturezaLesao();
		return naturezaLesao;
	}

	public void setNaturezaLesao(NaturezaLesao naturezaLesao)
	{
		this.naturezaLesao = naturezaLesao;
	}
	
	public Collection<NaturezaLesao> getNaturezaLesaos()
	{
		return naturezaLesaos;
	}
}