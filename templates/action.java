package com.fortes.rh.web.action.#NOME_PACOTE#;


import java.util.Collection;

import com.fortes.rh.business.#NOME_PACOTE#.#NOME_CLASSE#Manager;
import com.fortes.rh.model.#NOME_PACOTE#.#NOME_CLASSE#;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class #NOME_CLASSE#EditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private #NOME_CLASSE#Manager #NOME_CLASSE_MINUSCULO#Manager;
	private #NOME_CLASSE# #NOME_CLASSE_MINUSCULO#;
	private Collection<#NOME_CLASSE#> #NOME_CLASSE_MINUSCULO#s;

	private void prepare() throws Exception
	{
		if(#NOME_CLASSE_MINUSCULO# != null && #NOME_CLASSE_MINUSCULO#.getId() != null)
			#NOME_CLASSE_MINUSCULO# = (#NOME_CLASSE#) #NOME_CLASSE_MINUSCULO#Manager.findById(#NOME_CLASSE_MINUSCULO#.getId());

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
		#NOME_CLASSE_MINUSCULO#Manager.save(#NOME_CLASSE_MINUSCULO#);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		#NOME_CLASSE_MINUSCULO#Manager.update(#NOME_CLASSE_MINUSCULO#);
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		implementar
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			#NOME_CLASSE_MINUSCULO#Manager.remove(#NOME_CLASSE_MINUSCULO#.getId());
			addActionMessage("#NOME_CLASSE# excluído com sucesso.");verificar msg
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este #NOME_CLASSE_MINUSCULO#.");verificar msg
		}

		return list();
	}
	
	public #NOME_CLASSE# get#NOME_CLASSE#()
	{
		if(#NOME_CLASSE_MINUSCULO# == null)
			#NOME_CLASSE_MINUSCULO# = new #NOME_CLASSE#();
		return #NOME_CLASSE_MINUSCULO#;
	}

	public void set#NOME_CLASSE#(#NOME_CLASSE# #NOME_CLASSE_MINUSCULO#)
	{
		this.#NOME_CLASSE_MINUSCULO# = #NOME_CLASSE_MINUSCULO#;
	}

	public void set#NOME_CLASSE#Manager(#NOME_CLASSE#Manager #NOME_CLASSE_MINUSCULO#Manager)
	{
		this.#NOME_CLASSE_MINUSCULO#Manager = #NOME_CLASSE_MINUSCULO#Manager;
	}
	
	public Collection<#NOME_CLASSE#> get#NOME_CLASSE#s()
	{
		return #NOME_CLASSE_MINUSCULO#s;
	}
}