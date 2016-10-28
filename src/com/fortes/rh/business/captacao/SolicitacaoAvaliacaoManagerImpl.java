package com.fortes.rh.business.captacao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.SolicitacaoAvaliacaoDao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

@Component
public class SolicitacaoAvaliacaoManagerImpl extends GenericManagerImpl<SolicitacaoAvaliacao, SolicitacaoAvaliacaoDao> implements SolicitacaoAvaliacaoManager
{
	@Autowired
	SolicitacaoAvaliacaoManagerImpl(SolicitacaoAvaliacaoDao fooDao) {
        setDao(fooDao);
    }
	
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

	public Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId, Boolean responderModuloExterno) 
	{
		return getDao().findBySolicitacaoId(solicitacaoId, responderModuloExterno);
	}

	public void setResponderModuloExterno(Long solicitacaoId, Long[] solicitacaoAvaliacaoIds) 
	{
		getDao().setResponderModuloExterno(solicitacaoId, null, false);
		
		if (solicitacaoAvaliacaoIds != null && solicitacaoAvaliacaoIds.length > 0)
			getDao().setResponderModuloExterno(solicitacaoId, solicitacaoAvaliacaoIds, true);
	}

	public Collection<SolicitacaoAvaliacao> findAvaliacaoesNaoRespondidas(Long solicitacaoId, Long candidatoId) 
	{
		return getDao().findAvaliacaoesNaoRespondidas(solicitacaoId, candidatoId);
	}

	public void removeBySolicitacaoId(Long solicitacaoId) 
	{
		getDao().removeBySolicitacaoId(solicitacaoId);
	}
}