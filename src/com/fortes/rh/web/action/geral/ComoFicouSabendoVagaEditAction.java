package com.fortes.rh.web.action.geral;


import java.util.Collection;

import com.fortes.rh.business.geral.ComoFicouSabendoVagaManager;
import com.fortes.rh.model.geral.ComoFicouSabendoVaga;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ComoFicouSabendoVagaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ComoFicouSabendoVagaManager comoFicouSabendoVagaManager;
	private ComoFicouSabendoVaga comoFicouSabendoVaga;
	private Collection<ComoFicouSabendoVaga> comoFicouSabendoVagas;

	private void prepare() throws Exception
	{
		if(comoFicouSabendoVaga != null && comoFicouSabendoVaga.getId() != null)
			comoFicouSabendoVaga = (ComoFicouSabendoVaga) comoFicouSabendoVagaManager.findById(comoFicouSabendoVaga.getId());

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
		comoFicouSabendoVagaManager.save(comoFicouSabendoVaga);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		comoFicouSabendoVagaManager.update(comoFicouSabendoVaga);
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
			comoFicouSabendoVagaManager.remove(comoFicouSabendoVaga.getId());
			addActionMessage("ComoFicouSabendoVaga excluído com sucesso.");verificar msg
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir este comoFicouSabendoVaga.");verificar msg
		}

		return list();
	}
	
	public ComoFicouSabendoVaga getComoFicouSabendoVaga()
	{
		if(comoFicouSabendoVaga == null)
			comoFicouSabendoVaga = new ComoFicouSabendoVaga();
		return comoFicouSabendoVaga;
	}

	public void setComoFicouSabendoVaga(ComoFicouSabendoVaga comoFicouSabendoVaga)
	{
		this.comoFicouSabendoVaga = comoFicouSabendoVaga;
	}

	public void setComoFicouSabendoVagaManager(ComoFicouSabendoVagaManager comoFicouSabendoVagaManager)
	{
		this.comoFicouSabendoVagaManager = comoFicouSabendoVagaManager;
	}
	
	public Collection<ComoFicouSabendoVaga> getComoFicouSabendoVagas()
	{
		return comoFicouSabendoVagas;
	}
}
