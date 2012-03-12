package com.fortes.rh.business.sesmt;

import java.util.Collection;

import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.sesmt.SolicitacaoEpiItemEntregaManager;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemEntregaDao;

public class SolicitacaoEpiItemEntregaManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemEntrega, SolicitacaoEpiItemEntregaDao> implements SolicitacaoEpiItemEntregaManager
{
	public Collection<SolicitacaoEpiItemEntrega> findBySolicitacaoEpiItem(Long solicitacaoEpiItemId) 
	{
		return getDao().findBySolicitacaoEpiItem(solicitacaoEpiItemId);
	}
}
