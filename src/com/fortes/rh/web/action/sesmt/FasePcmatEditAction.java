package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.sesmt.FasePcmatManager;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class FasePcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private FasePcmatManager fasePcmatManager;
	private FasePcmat fasePcmat;
	private Collection<FasePcmat> fasePcmats;
	private String descricao;

	private void prepare() throws Exception
	{
		if(fasePcmat != null && fasePcmat.getId() != null)
			fasePcmat = (FasePcmat) fasePcmatManager.findById(fasePcmat.getId());
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
		try {
			fasePcmat.setEmpresa(getEmpresaSistema());
			fasePcmatManager.save(fasePcmat);
			
			addActionSuccess("Fase cadastrada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível cadastrar a fase.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			fasePcmatManager.update(fasePcmat);
			addActionSuccess("Fase atualizada com sucesso.");
			
		} catch (Exception e) {
			prepare();
			e.printStackTrace();
			addActionError("Não foi possível atualizar a fase.");
			
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		fasePcmats = fasePcmatManager.findAllSelect(descricao, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			fasePcmatManager.remove(fasePcmat.getId());
			addActionSuccess("Fase excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta fase.");
		}

		return list();
	}
	
	public FasePcmat getFasePcmat()
	{
		if(fasePcmat == null)
			fasePcmat = new FasePcmat();
		return fasePcmat;
	}

	public void setFasePcmat(FasePcmat fasePcmat)
	{
		this.fasePcmat = fasePcmat;
	}

	public void setFasePcmatManager(FasePcmatManager fasePcmatManager)
	{
		this.fasePcmatManager = fasePcmatManager;
	}
	
	public Collection<FasePcmat> getFasePcmats()
	{
		return fasePcmats;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
