package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDao;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.EpiHistorico;
import com.fortes.rh.model.sesmt.MotivoSolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItem;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemEntrega;
import com.fortes.rh.model.sesmt.TamanhoEPI;

@Component
public class SolicitacaoEpiItemManagerImpl extends GenericManagerImpl<SolicitacaoEpiItem, SolicitacaoEpiItemDao> implements SolicitacaoEpiItemManager
{
	@Autowired private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	@Autowired private SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager;
	@Autowired private EpiHistoricoManager epiHistoricoManager;
	
	@Autowired
	SolicitacaoEpiItemManagerImpl(SolicitacaoEpiItemDao solicitacaoEpiItemDao) {
		setDao(solicitacaoEpiItemDao);
	}
	
	public Collection<SolicitacaoEpiItem> findBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		Collection<SolicitacaoEpiItem> solicitacaoEpiItems = getDao().findBySolicitacaoEpi(solicitacaoEpiId);
		
		for (SolicitacaoEpiItem solicitacaoEpiItem : solicitacaoEpiItems) 
			populaEPIsEntreguesDevolvidos(solicitacaoEpiItem);

		return solicitacaoEpiItems;
	}
	
	public SolicitacaoEpiItem populaEPIsEntreguesDevolvidos(SolicitacaoEpiItem solicitacaoEpiItem)
	{
		Collection<SolicitacaoEpiItemEntrega> entregas =  solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(solicitacaoEpiItem.getId());
		solicitacaoEpiItem.setSolicitacaoEpiItemEntregas(entregas);
		
		Collection<SolicitacaoEpiItemDevolucao> devolucoes =  solicitacaoEpiItemDevolucaoManager.findSolicEpiItemDevolucaoBySolicitacaoEpiItem(solicitacaoEpiItem.getId());
		solicitacaoEpiItem.setSolicitacaoEpiItemDevolucoes(devolucoes);
		
		for (SolicitacaoEpiItemEntrega entrega : entregas)
			solicitacaoEpiItem.setTotalEntregue(solicitacaoEpiItem.getTotalEntregue() + entrega.getQtdEntregue());
		
		for (SolicitacaoEpiItemDevolucao devolucao : devolucoes)
			solicitacaoEpiItem.setTotalDevolvido(solicitacaoEpiItem.getTotalDevolvido() + devolucao.getQtdDevolvida());
		
		return solicitacaoEpiItem;
	}

	public void save(SolicitacaoEpi solicitacaoEpi, String[] epiIds, String[] selectQtdSolicitado, String[] selectMotivoSolicitacaoEpi, Date dataEntrega, boolean entregue, String[] selectTamanhoEpi)
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
				
				MotivoSolicitacaoEpi motivoSolicitacaoEpi = new MotivoSolicitacaoEpi();
				if (StringUtils.isBlank(selectMotivoSolicitacaoEpi[i]))
					motivoSolicitacaoEpi = null;
				else 
					motivoSolicitacaoEpi.setId(Long.valueOf(selectMotivoSolicitacaoEpi[i]));
				
				TamanhoEPI tamanhoEPI;
				if (selectTamanhoEpi == null || StringUtils.isBlank(selectTamanhoEpi[i])) {
					tamanhoEPI = null;
				} else {
					tamanhoEPI = new TamanhoEPI();
					tamanhoEPI.setId(Long.valueOf(selectTamanhoEpi[i]));
				}
				
				solicitacaoEpiItem.setQtdSolicitado(Integer.valueOf(selectQtdSolicitado[i]));
				solicitacaoEpiItem.setMotivoSolicitacaoEpi(motivoSolicitacaoEpi);
				solicitacaoEpiItem.setTamanhoEPI(tamanhoEPI);
				
				solicitacaoEpiItem.setEpi(epiTmp);
				solicitacaoEpiItem.setSolicitacaoEpi(solicitacaoEpi);

				getDao().save(solicitacaoEpiItem);

				if (entregue)
				{
					EpiHistorico epiHistorico = epiHistoricoManager.findUltimoByEpiId(epiTmp.getId());
					
					SolicitacaoEpiItemEntrega entrega = new SolicitacaoEpiItemEntrega();
					entrega.setSolicitacaoEpiItem(solicitacaoEpiItem);
					entrega.setQtdEntregue(Integer.valueOf(selectQtdSolicitado[i]));
					entrega.setDataEntrega(dataEntrega);
					entrega.setEpiHistorico(epiHistorico);
					
					solicitacaoEpiItemEntregaManager.save(entrega);
				}
			}
		}
	}

	public void removeAllBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		getDao().removeAllBySolicitacaoEpi(solicitacaoEpiId);
	}

	public Collection<SolicitacaoEpiItem> findAllEntregasBySolicitacaoEpi(Long solicitacaoEpiId)
	{
		return getDao().findAllEntregasBySolicitacaoEpi(solicitacaoEpiId);
	}

	public Collection<SolicitacaoEpiItem> findAllDevolucoesBySolicitacaoEpi(Long solicitacaoEpiId) {
		return getDao().findAllDevolucoesBySolicitacaoEpi(solicitacaoEpiId);
	}

	public SolicitacaoEpiItem findByIdProjection(Long id) 
	{
		return getDao().findByIdProjection(id);
	}
	
	public Integer countByTipoEPIAndTamanhoEPI(Long tipoEPIId, Long tamanhoEPIId) {
		return getDao().countByTipoEPIAndTamanhoEPI(tipoEPIId, tamanhoEPIId);
	}
}