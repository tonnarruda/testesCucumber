package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import com.fortes.rh.model.sesmt.SolicitacaoEpi;

public class SolicitacaoEpiVO
{
	private Integer qtdSolicitacaoEpis = 0;
	private Collection<SolicitacaoEpi> solicitacaoEpis;
	
	public Integer getQtdSolicitacaoEpis() {
			return qtdSolicitacaoEpis;
	}
	public void setQtdSolicitacaoEpis(Integer qtdSolicitacaoEpis) {
		this.qtdSolicitacaoEpis = qtdSolicitacaoEpis;
	}
	public Collection<SolicitacaoEpi> getSolicitacaoEpis() {
		return solicitacaoEpis;
	}
	public void setSolicitacaoEpis(Collection<SolicitacaoEpi> solicitacaoEpis) {
		this.solicitacaoEpis = solicitacaoEpis;
	}
}