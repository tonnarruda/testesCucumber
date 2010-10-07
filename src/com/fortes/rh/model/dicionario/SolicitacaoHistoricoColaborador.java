package com.fortes.rh.model.dicionario;

import java.util.Collection;

import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;

public class SolicitacaoHistoricoColaborador
{
	private Solicitacao solicitacao;
	private Collection<HistoricoCandidato> historicos;

	public Collection<HistoricoCandidato> getHistoricos()
	{
		return historicos;
	}
	public void setHistoricos(Collection<HistoricoCandidato> historicos)
	{
		this.historicos = historicos;
	}
	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

}
