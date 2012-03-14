package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public interface SolicitacaoEpiItemManager extends GenericManager<SolicitacaoEpiItem>
{
	Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId);
	void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, Date dataEntrega);
	void removeAllBySolicitacaoEpi(Long solicitacaoEpiId);
	Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long[] solicitacaoEpiIds);
}