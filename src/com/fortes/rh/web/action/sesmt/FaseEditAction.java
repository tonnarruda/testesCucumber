package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.FaseManager;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class FaseEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	@Autowired private FaseManager faseManager;
	private Fase fase;
	private Collection<Fase> fases;
	private String descricao;

	private void prepare() throws Exception
	{
		if(fase != null && fase.getId() != null)
			fase = (Fase) faseManager.findById(fase.getId());
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
			fase.setEmpresa(getEmpresaSistema());
			faseManager.save(fase);
			
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
			faseManager.update(fase);
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
		fases = faseManager.findAllSelect(descricao, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			faseManager.remove(fase.getId());
			addActionSuccess("Fase excluída com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esta fase.");
		}

		return list();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Fase getFase()
	{
		if(fase == null)
			fase = new Fase();
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public Collection<Fase> getFases() {
		return fases;
	}

	public void setFases(Collection<Fase> fases) {
		this.fases = fases;
	}
}