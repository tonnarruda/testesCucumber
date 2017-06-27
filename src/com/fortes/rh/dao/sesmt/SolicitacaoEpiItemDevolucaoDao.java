package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public interface SolicitacaoEpiItemDevolucaoDao extends GenericDao<SolicitacaoEpiItemDevolucao>
{
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);
	public Collection<SolicitacaoEpiItemDevolucao> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
	public Integer findQtdDevolvidaByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId);
	public Collection<SolicitacaoEpiItemDevolucao> findQtdDevolvidaBySolicitacaoItemIds(Long[] solicitacaoEpiItensId);
}
