package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;

public class SolicitacaoEpiItemManagerImpl extends GenericManagerImpl<SolicitacaoEpiItem, SolicitacaoEpiItemDao> implements SolicitacaoEpiItemManager
{
	private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	
	public Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = getDao().findBySolicitacaoEpi(solicitacaoEpiId);
		
		for (SolicitacaoEpiItem solicitacaoEpiItem : solicitacaoEpiItems) 
		{
			Collection<SolicitacaoEpiItemEntrega> entregas =  solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(solicitacaoEpiItem.getId());
			solicitacaoEpiItem.setSolicitacaoEpiItemEntregas(entregas);
			
			for (SolicitacaoEpiItemEntrega entrega : entregas)
				solicitacaoEpiItem.setTotalEntregue(solicitacaoEpiItem.getTotalEntregue() + entrega.getQtdEntregue());
		}
		
		return solicitacaoEpiItems;
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, Date dataEntrega, boolean entregue)
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
				
				solicitacaoEpiItem.setEpi(epiTmp);
				solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);

				getDao().save(solicitacaoEpiItem);

				if (entregue)
				{
					SolicitacaoEpiItemEntrega entrega = new SolicitacaoEpiItemEntrega();
					entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
					entrega.setQtdEntregue(Integer.valueOf(selectQtdSolicitado[i]));
					entrega.setDataEntrega(dataEntrega);
					
					solicitacaoEpiItemEntregaManager.save(entrega);
				}
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

	public void setSolicitacaoEpiItemEntregaManager(SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager) {
		this.solicitacaoEpiItemEntregaManager = solicitacaoEpiItemEntregaManager;
	}

	public SolicitacaoEpiItem findByIdProjection(Long id) {
		return getDao().findByIdProjection(id);
	}
}