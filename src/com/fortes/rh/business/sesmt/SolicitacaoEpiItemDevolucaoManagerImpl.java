package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;

@Component
public class SolicitacaoEpiItemDevolucaoManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemDevolucao, SolicitacaoEpiItemDevolucaoDao> implements SolicitacaoEpiItemDevolucaoManager{
	
	@Autowired
	SolicitacaoEpiItemDevolucaoManagerImpl(SolicitacaoEpiItemDevolucaoDao solicitacaoEpiItemDevolucaoDao) {
		setDao(solicitacaoEpiItemDevolucaoDao);
	}
	
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		return getDao().getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);
	}

	public Collection<SolicitacaoEpiItemDevolucao> findSolicEpiItemDevolucaoBySolicitacaoEpiItem(Long solicitcaoEpiItemId) {
		return getDao().findBySolicitacaoEpiItem(solicitcaoEpiItemId);
	}
}
