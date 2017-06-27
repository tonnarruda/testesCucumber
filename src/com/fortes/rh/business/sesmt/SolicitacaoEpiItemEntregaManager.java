package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public interface SolicitacaoEpiItemEntregaManager extends GenericManager<SolicitacaoEpiItemEntrega>
{
	Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
	int getTotalEntregue(Long solicitacaoEpiItemId, Long solicitacaoEpiItemEntregaId);
	SolicitacaoEpiItemEntrega findByIdProjection(Long solicitacaoEpiItemEntregaId);
	boolean existeEntrega(Long solicitacaoEpiId);
	Integer findQtdEntregueByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId);
	Date getMinDataBySolicitacaoEpiItem(Long solicitacaoEpiItemId);
}
