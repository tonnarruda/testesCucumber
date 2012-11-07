package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

public interface SolicitacaoAvaliacaoManager extends GenericManager<SolicitacaoAvaliacao>
{
	void saveAvaliacoesSolicitacao(Long solicitacaoId, Long[] avaliacaoIds);
	Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId, Boolean responderModuloExterno);
	void setResponderModuloExterno(Long solicitacaoId, Long[] solicitacaoAvaliacaoIds);
}