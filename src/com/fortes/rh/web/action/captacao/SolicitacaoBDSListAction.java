/* Autor: Robertson Freitas
 * Data: 04/07/2006
 * Requisito: RFA019 - Solicitar Banco de Dados Solidário */
package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.SolicitacaoBDSManager;
import com.fortes.rh.model.captacao.SolicitacaoBDS;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class SolicitacaoBDSListAction extends MyActionSupportList
{
	@Autowired private SolicitacaoBDSManager solicitacaoBDSManager;
	private Collection<SolicitacaoBDS> solicitacaoBDSs;
	private SolicitacaoBDS solicitacaoBDS;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		solicitacaoBDSs = solicitacaoBDSManager.findAll();

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		solicitacaoBDSManager.remove(new Long[] { solicitacaoBDS.getId() });

		return Action.SUCCESS;
	}

	public Collection<SolicitacaoBDS> getSolicitacaoBDSs()
	{
		return solicitacaoBDSs;
	}

	public SolicitacaoBDS getSolicitacaoBDS()
	{
		if (solicitacaoBDS == null)
		{
			solicitacaoBDS = new SolicitacaoBDS();
		}
		return solicitacaoBDS;
	}

	public void setSolicitacaoBDS(SolicitacaoBDS solicitacaoBDS)
	{
		this.solicitacaoBDS = solicitacaoBDS;
	}
}