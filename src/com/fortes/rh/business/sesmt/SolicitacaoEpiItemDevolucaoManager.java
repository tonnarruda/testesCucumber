package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public interface SolicitacaoEpiItemDevolucaoManager extends GenericManager<SolicitacaoEpiItemDevolucao>
{
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);

	public Collection<SolicitacaoEpiItemDevolucao> findSolicEpiItemDevolucaoBySolicitacaoEpiItem(Long solicitcaoEpiItemId);
}