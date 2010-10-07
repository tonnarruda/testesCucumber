package com.fortes.rh.web.action.captacao;

import java.util.Collection;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class HistoricoCandidatoListAction extends MyActionSupportList
{
	private HistoricoCandidatoManager historicoCandidatoManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;

	private Collection<HistoricoCandidato> historicoCandidatos;
	private HistoricoCandidato historicoCandidato;
	private CandidatoSolicitacao candidatoSolicitacao;

	private Long etapaSeletivaId;
	private char visualizar;
	private String indicadoPor;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		candidatoSolicitacao = candidatoSolicitacaoManager.findByCandidatoSolicitacao(candidatoSolicitacao);
		historicoCandidatos = historicoCandidatoManager.findList(candidatoSolicitacao);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		historicoCandidatoManager.remove(new Long[]{historicoCandidato.getId()});
		return Action.SUCCESS;
	}

	public Collection getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}

	public CandidatoSolicitacao getCandidatoSolicitacao()
	{
		return candidatoSolicitacao;
	}

	public void setCandidatoSolicitacao(CandidatoSolicitacao candidatoSolicitacao)
	{
		this.candidatoSolicitacao = candidatoSolicitacao;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public HistoricoCandidato getHistoricoCandidato()
	{
		return historicoCandidato;
	}

	public void setHistoricoCandidato(HistoricoCandidato historicoCandidato)
	{
		this.historicoCandidato = historicoCandidato;
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}
	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}
}