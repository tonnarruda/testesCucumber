package com.fortes.rh.model.geral.relatorio;

import java.io.Serializable;
import java.util.Collection;

import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;

// Utilizado para gerar curriculo do candidato mais historico.
public class CurriculoCandidatoRelatorio implements Serializable
{
	private Collection<SolicitacaoHistoricoColaborador> historicos;
	private Candidato candidatos;
	private Solicitacao solicitacao;

	public Candidato getCandidatos()
	{
		return candidatos;
	}

	public void setCandidatos(Candidato candidatos)
	{
		this.candidatos = candidatos;
	}

	public Collection<SolicitacaoHistoricoColaborador> getHistoricos()
	{
		return historicos;
	}

	public void setHistoricos(Collection<SolicitacaoHistoricoColaborador> historicos)
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
