package com.fortes.rh.web.action.cargosalario;

import com.fortes.portalcolaborador.business.MovimentacaoOperacaoPCManager;
import com.fortes.portalcolaborador.business.operacao.AtualizarHistoricoIndice;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings( { "serial" })
public class IndiceHistoricoEditAction extends MyActionSupportEdit implements ModelDriven
{
	private IndiceHistoricoManager indiceHistoricoManager;
	private IndiceManager indiceManager;

	private IndiceHistorico indiceHistorico;
	private Indice indiceAux;
	private boolean integradoAC;
	private MovimentacaoOperacaoPCManager movimentacaoOperacaoPCManager;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{			
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no AC Pessoal.");
		}
		else
		{
			indiceAux = indiceManager.findByIdProjection(indiceAux.getId());
			indiceHistorico.setIndice(indiceAux);			
		}

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{			
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no AC Pessoal.");
		}
		else
		{
			if (indiceHistorico != null && indiceHistorico.getId() != null)
			{
				indiceHistorico = (IndiceHistorico) indiceHistoricoManager.findByIdProjection(indiceHistorico.getId());
				indiceAux = indiceHistorico.getIndice();
			}
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{			
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no AC Pessoal.");
		}
		else
		{
			if(indiceHistoricoManager.verifyData(indiceHistorico.getId(), indiceHistorico.getData(), indiceAux.getId()))
			{
				addActionMessage("Já existe um histórico com essa data.");
				prepareInsert();
				return Action.INPUT;
			}

			indiceHistoricoManager.save(indiceHistorico);
			movimentacaoOperacaoPCManager.enfileirar(AtualizarHistoricoIndice.class, indiceAux.getIdentificadorToJson(), getEmpresaSistema().isIntegradaPortalColaborador());
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		integradoAC = getEmpresaSistema().isAcIntegra();
		if(integradoAC)
		{			
			addActionMessage("A manutenção no cadastro de índice deve ser realizada no AC Pessoal.");
		}
		else
		{
			if(indiceHistoricoManager.verifyData(indiceHistorico.getId(), indiceHistorico.getData(), indiceAux.getId()))
			{
				addActionMessage("Já existe um histórico com essa data.");
				prepareUpdate();
				return Action.INPUT;
			}

			indiceHistoricoManager.update(indiceHistorico);	
			movimentacaoOperacaoPCManager.enfileirar(AtualizarHistoricoIndice.class, indiceAux.getIdentificadorToJson(), getEmpresaSistema().isIntegradaPortalColaborador());
		}

		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getIndiceHistorico();
	}

	public IndiceHistorico getIndiceHistorico()
	{
		if (indiceHistorico == null)
			indiceHistorico = new IndiceHistorico();
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

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}

	public void setMovimentacaoOperacaoPCManager(
			MovimentacaoOperacaoPCManager movimentacaoOperacaoPCManager) {
		this.movimentacaoOperacaoPCManager = movimentacaoOperacaoPCManager;
	}
}