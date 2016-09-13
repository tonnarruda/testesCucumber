package com.fortes.rh.model.sesmt.relatorio;

import java.util.Collection;

import com.fortes.rh.model.sesmt.SolicitacaoEpi;

public class SolicitacaoEpiVO
{
	private Integer qtdSolicitacaoEpis;
	private Collection<SolicitacaoEpi> solicitacaoEpis;
	
	public Integer getQtdSolicitacaoEpis() {
		if(this.qtdSolicitacaoEpis != null)
			return qtdSolicitacaoEpis;
		else
			return 0;
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