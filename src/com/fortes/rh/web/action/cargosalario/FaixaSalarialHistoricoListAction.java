package com.fortes.rh.web.action.cargosalario;

import java.lang.reflect.InvocationTargetException;

import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.exception.ESocialException;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
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
			if(isEmpresaIntegradaEAderiuAoESocial() && faixaSalarialHistorico.getStatus() != StatusRetornoAC.CANCELADO)
				throw new ESocialException();
			else
				faixaSalarialHistoricoManager.remove(faixaSalarialHistorico.getId(), getEmpresaSistema(), true);
		}

		catch(InvocationTargetException e)
		{
			e.printStackTrace();

			if(e.getTargetException() instanceof FortesException)
				setActionMsg(e.getTargetException().getMessage());
			
			if(e.getTargetException() instanceof IntegraACException)
				setActionMsg("Não foi possível excluir o histórico da faixa salarial no Fortes Pessoal.");
		}
		catch (ESocialException e) {
			setActionMsg(e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setActionMsg("Não foi possível excluir o histórico faixa salarial. Pois possui dependências.");
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