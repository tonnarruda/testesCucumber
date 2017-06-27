package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public class SolicitacaoEpiItemEntregaManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemEntrega, SolicitacaoEpiItemEntregaDao> implements SolicitacaoEpiItemEntregaManager
{
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) 
	{
		return getDao().findBySolicitacaoEpiItem(solicitacaoEpiItemId);
	}

	public int getTotalEntregue(Long solicitacaoEpiItemId, Long solicitacaoEpiItemEntregaId) 
	{
		return getDao().getTotalEntregue(solicitacaoEpiItemId, solicitacaoEpiItemEntregaId);
	}

	public SolicitacaoEpiItemEntrega findByIdProjection(Long solicitacaoEpiItemEntregaId) 
	{
		return getDao().findByIdProjection(solicitacaoEpiItemEntregaId);
	}

	public boolean existeEntrega(Long solicitacaoEpiId) 
	{
		return getDao().findBySolicitacaoEpi(solicitacaoEpiId).size() > 0;
	}

	public Integer findQtdEntregueByDataAndSolicitacaoItemId(Date data,Long solicitacaoEpiItemId) 
	{
		return getDao().findQtdEntregueByDataAndSolicitacaoItemId(data, solicitacaoEpiItemId);
	}

	public Date getMinDataBySolicitacaoEpiItem(Long solicitacaoEpiItemId) {
		return getDao().getMinDataBySolicitacaoEpiItem(solicitacaoEpiItemId);
	}
}
