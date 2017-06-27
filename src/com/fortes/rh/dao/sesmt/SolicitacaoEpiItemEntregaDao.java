package com.fortes.rh.dao.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public interface SolicitacaoEpiItemEntregaDao extends GenericDao<SolicitacaoEpiItemEntrega> 
{
	Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
	int getTotalEntregue(Long solicitacaoEpiItemId, Long solicitacaoEpiItemEntregaId);
	SolicitacaoEpiItemEntrega findByIdProjection(Long solicitacaoEpiItemEntregaId);
	Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpi(Long solicitacaoEpiId);
	Integer findQtdEntregueByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId);
	Date getMinDataBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
}
