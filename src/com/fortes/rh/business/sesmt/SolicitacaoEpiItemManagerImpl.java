package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;

public class SolicitacaoEpiItemManagerImpl extends GenericManagerImpl<SolicitacaoEpiItem, SolicitacaoEpiItemDao> implements SolicitacaoEpiItemManager
{
	public Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		return getDao().findBySolicitacaoEpi(solicitacaoEpiId);
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado)
	{
		if (epiIds != null && selectQtdSolicitado != null)
		{
			for (int i=0; i<epiIds.length; i++)
			{
				SolicitacaoEpiItem solicitacaoEpiItem = new SolicitacaoEpiItem();

				Epi epiTmp = new Epi();
				epiTmp.setId(Long.valueOf(epiIds[i]));

				if (StringUtils.isBlank(selectQtdSolicitado[i]))
					selectQtdSolicitado[i] = "0";
				solicitacaoEpiItem.setQtdSolicitado(Integer.valueOf(selectQtdSolicitado[i]));
				if(solicitacaoEpi.isEntregue())
					solicitacaoEpiItem.setQtdEntregue(Integer.valueOf(selectQtdSolicitado[i]));
				else
					solicitacaoEpiItem.setQtdEntregue(0);
				
				solicitacaoEpiItem.setEpi(epiTmp);
				solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);

				getDao().save(solicitacaoEpiItem);
			}
		}
	}

	public void entrega(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado)
	{
		if (epiIds != null && selectQtdSolicitado != null)
		{
			for (int i=0; i < epiIds.length; i++)
			{
				SolicitacaoEpiItem solicitacaoEpiItem = getDao().findBySolicitacaoEpiAndEpi(solicitacaoEpi.getId(), Long.valueOf(epiIds[i]));
				solicitacaoEpiItem.setQtdEntregue(Integer.valueOf(selectQtdSolicitado[i]));
				getDao().update(solicitacaoEpiItem);
			}
		}
	}

	public void removeAllBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		getDao().removeAllBySolicitacaoEpi(solicitacaoEpiId);
	}

	public Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long[] solicitacaoEpiIds)
	{
		if (solicitacaoEpiIds == null || solicitacaoEpiIds.length == 0)
			return new ArrayList<SolicitacaoEpiItem>();

		return getDao().findBySolicitacaoEpi(solicitacaoEpiIds);
	}

}