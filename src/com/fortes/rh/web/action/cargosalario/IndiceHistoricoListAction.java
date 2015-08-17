package com.fortes.rh.web.action.cargosalario;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class IndiceHistoricoListAction extends MyActionSupportList
{
	private IndiceHistoricoManager indiceHistoricoManager;

	private IndiceHistorico indiceHistorico;
	private Indice indiceAux;

	private boolean integradoAC;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no Fortes Pessoal.");
		else
			indiceHistoricoManager.remove(indiceHistorico.getId());
		
		return Action.SUCCESS;
	}

	public IndiceHistorico getIndiceHistorico()
	{
		if (indiceHistorico == null)
		{
			indiceHistorico = new IndiceHistorico();
		}
		return indiceHistorico;
	}

	public void setIndiceHistorico(IndiceHistorico indiceHistorico)
	{
		this.indiceHistorico = indiceHistorico;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public Indice getIndiceAux()
	{
		return indiceAux;
	}

	public void setIndiceAux(Indice indiceAux)
	{
		this.indiceAux = indiceAux;
	}
}