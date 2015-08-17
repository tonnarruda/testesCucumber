package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class IndiceEditAction extends MyActionSupportEdit implements ModelDriven
{
	private IndiceManager indiceManager;
	private IndiceHistoricoManager indiceHistoricoManager;

	private Indice indiceAux;
	private IndiceHistorico indiceHistorico;

	private Collection<IndiceHistorico> indicesHistoricos = new ArrayList<IndiceHistorico>();
	private boolean integradoAC;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		verificaIntegracaoAC();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		if (indiceAux != null && indiceAux.getId() != null)
		{
			indiceAux = (Indice) indiceManager.findById(indiceAux.getId());
			indicesHistoricos = indiceHistoricoManager.findAllSelect(indiceAux.getId());
		}

		verificaIntegracaoAC();

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no Fortes Pessoal.");
		}
		else
		{
			indiceManager.save(indiceAux, indiceHistorico);
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no Fortes Pessoal.");
		}
		else
		{
			indiceManager.update(indiceAux);
		}

		return Action.SUCCESS;
	}

	private void verificaIntegracaoAC()
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no Fortes Pessoal.");
	}

	public Object getModel()
	{
		return getIndiceAux();
	}

	public Indice getIndiceAux()
	{
		if (indiceAux == null)
			indiceAux = new Indice();
		return indiceAux;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setIndiceHistoricoManager(IndiceHistoricoManager indiceHistoricoManager)
	{
		this.indiceHistoricoManager = indiceHistoricoManager;
	}

	public Collection<IndiceHistorico> getIndicesHistoricos()
	{
		return this.indicesHistoricos;
	}

	public void setIndiceAux(Indice indiceAux)
	{
		this.indiceAux = indiceAux;
	}

	public IndiceHistorico getIndiceHistorico()
	{
		return indiceHistorico;
	}

	public void setIndiceHistorico(IndiceHistorico indiceHistorico)
	{
		this.indiceHistorico = indiceHistorico;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}
}