package com.fortes.rh.business.sesmt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

@Component
public class SolicitacaoEpiItemEntregaManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemEntrega, SolicitacaoEpiItemEntregaDao> implements SolicitacaoEpiItemEntregaManager
{
	@Autowired
	SolicitacaoEpiItemEntregaManagerImpl(SolicitacaoEpiItemEntregaDao solicitacaoEpiItemEntregaDao) {
		setDao(solicitacaoEpiItemEntregaDao);
	}
	
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
}
