package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public interface SolicitacaoEpiItemDevolucaoManager extends GenericManager<SolicitacaoEpiItemDevolucao>
{
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);
	public Collection<SolicitacaoEpiItemDevolucao> findSolicEpiItemDevolucaoBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
	public Integer findQtdDevolvidaByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);
	public Collection<SolicitacaoEpi> removeItensDevolvidos(Collection<SolicitacaoEpi> solicitacaoEpis) throws ColecaoVaziaException;
}