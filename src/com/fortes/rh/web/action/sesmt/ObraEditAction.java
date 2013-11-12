package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ObraEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private ObraManager obraManager;
	
	private Obra obra;
	
	private Collection<Obra> obras;
	
	private String nome;

	private void prepare() throws Exception
	{
		if (obra != null && obra.getId() != null)
			obra = (Obra) obraManager.findById(obra.getId());
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
		obra.setEmpresa(getEmpresaSistema());
		obraManager.save(obra);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		obraManager.update(obra);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		obras = obraManager.findAllSelect(nome, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			obraManager.remove(obra.getId());
			addActionSuccess("Obra excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta obra.");
		}

		return list();
	}
	
	public Obra getObra()
	{
		if(obra == null)
			obra = new Obra();
		return obra;
	}

	public void setObra(Obra obra)
	{
		this.obra = obra;
	}

	public void setObraManager(ObraManager obraManager)
	{
		this.obraManager = obraManager;
	}
	
	public Collection<Obra> getObras()
	{
		return obras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
