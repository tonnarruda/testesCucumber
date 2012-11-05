package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

public class SolicitacaoAvaliacaoManagerImpl extends GenericManagerImpl<SolicitacaoAvaliacao, SolicitacaoAvaliacaoDao> implements SolicitacaoAvaliacaoManager
{
	public void saveAvaliacoesSolicitacao(Long solicitacaoId, Long[] avaliacaoIds) 
	{
		getDao().removeBySolicitacaoId(solicitacaoId);
		
		SolicitacaoAvaliacao solicitacaoAvaliacao;
		
		for (Long avaliacaoId : avaliacaoIds) 
		{
			solicitacaoAvaliacao = new SolicitacaoAvaliacao();
			solicitacaoAvaliacao.setProjectionAvaliacaoId(avaliacaoId);
			solicitacaoAvaliacao.setProjectionSolicitacaoId(solicitacaoId);
			save(solicitacaoAvaliacao);
		}
	}

	public Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId) 
	{
		return getDao().findBySolicitacaoId(solicitacaoId);
	}
}