package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public interface SolicitacaoEpiItemDevolucaoDao extends GenericDao<SolicitacaoEpiItemDevolucao>
{

	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);

	public Collection<SolicitacaoEpiItemDevolucao> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId);

}
