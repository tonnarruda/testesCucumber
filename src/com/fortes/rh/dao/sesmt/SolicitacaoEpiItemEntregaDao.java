package com.fortes.rh.dao.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public interface SolicitacaoEpiItemEntregaDao extends GenericDao<SolicitacaoEpiItemEntrega> 
{
	Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
	int getTotalEntregue(Long solicitacaoEpiItemId, Long solicitacaoEpiItemEntregaId);
}
