package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

public interface SolicitacaoAvaliacaoManager extends GenericManager<SolicitacaoAvaliacao>
{
	void saveAvaliacoesSolicitacao(Long solicitacaoId, Long[] avaliacaoIds);
	Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId, Boolean responderModuloExterno);
	void setResponderModuloExterno(Long solicitacaoId, Long[] solicitacaoAvaliacaoIds);
	Collection<SolicitacaoAvaliacao> findAvaliacaoesNaoRespondidas(Long solicitacaoId, Long candidatoId);
	void removeBySolicitacaoId(Long solicitacaoId);
	void inserirNovasAvaliações(Long solicitacaoId, Collection<Avaliacao> avaliacoes);
}