package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;

public interface SolicitacaoAvaliacaoDao extends GenericDao<SolicitacaoAvaliacao>
{
	void removeBySolicitacaoId(Long solicitacaoId);
	Collection<SolicitacaoAvaliacao> findBySolicitacaoId(Long solicitacaoId);
}