package com.fortes.rh.web.action.cargosalario;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class FaixaSalarialHistoricoListAction extends MyActionSupportList
{
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;

	private FaixaSalarialHistorico faixaSalarialHistorico;
	private FaixaSalarial faixaSalarialAux;
	
	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			faixaSalarialHistoricoManager.remove(faixaSalarialHistorico.getId(), getEmpresaSistema(), true);
		}
		catch(IntegraACException e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir o histórico da faixa salarial no AC Pessoal.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir o histórico faixa salarial.");
		}
		
		return Action.SUCCESS;
	}

	public void setFaixaSalarialHistorico(FaixaSalarialHistorico faixaSalarialHistorico)
	{
		this.faixaSalarialHistorico = faixaSalarialHistorico;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public FaixaSalarial getFaixaSalarialAux()
	{
		return faixaSalarialAux;
	}

	public void setFaixaSalarialAux(FaixaSalarial faixaSalarialAux)
	{
		this.faixaSalarialAux = faixaSalarialAux;
	}
}