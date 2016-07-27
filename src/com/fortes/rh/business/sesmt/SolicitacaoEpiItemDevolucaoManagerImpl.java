package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

public class SolicitacaoEpiItemDevolucaoManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemDevolucao, SolicitacaoEpiItemDevolucaoDao> implements SolicitacaoEpiItemDevolucaoManager{
	
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		return getDao().getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);
	}

	public Collection<SolicitacaoEpiItemDevolucao> findSolicEpiItemDevolucaoBySolicitacaoEpiItem(Long solicitcaoEpiItemId) {
		return getDao().findBySolicitacaoEpiItem(solicitcaoEpiItemId);
	}
}
