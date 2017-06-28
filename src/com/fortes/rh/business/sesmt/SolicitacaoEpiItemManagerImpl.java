package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

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
import com.fortes.rh.util.DateUtil;

public class SolicitacaoEpiItemManagerImpl extends GenericManagerImpl<SolicitacaoEpiItem, SolicitacaoEpiItemDao> implements SolicitacaoEpiItemManager
{
	private SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager;
	private SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager;
	private EpiHistoricoManager epiHistoricoManager;
	
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
		decideEdicaoSolicitacaoEpiItemEntrega(solicitacaoEpiItem.getId(), entregas);
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
	
	public String validaDataDevolucao(Date data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId, Integer qtdASerDevolvida, Date solicitacaoEpiData) {
		if (solicitacaoEpiData != null && data.getTime() < solicitacaoEpiData.getTime())
			return "A data de devolução não pode ser anterior à data de solicitação ( Data solicitação:" + DateUtil.formataDiaMesAno(solicitacaoEpiData) + " )";
		
		Date dataPrimeiraEntrega = solicitacaoEpiItemEntregaManager.getMinDataBySolicitacaoEpiItem(solicitacaoEpiItemId);
		
		if(dataPrimeiraEntrega != null && data.getTime() < dataPrimeiraEntrega.getTime())
			return "Não é possível inserir uma devolução anterior a primeira data de entrega ( Data:" + DateUtil.formataDiaMesAno(dataPrimeiraEntrega) +  " )";
		
		Integer qtdTotalEntregue = solicitacaoEpiItemEntregaManager.getTotalEntregue(solicitacaoEpiItemId, null);
		Integer qtdTotalDevolvida = solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);

		if(qtdTotalDevolvida >= qtdTotalEntregue)
			return "Não é possível inserir uma devolução, pois todas os ítens já foram devolvidos.";
		
		Integer qtdEntregueAteAData = solicitacaoEpiItemEntregaManager.findQtdEntregueByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId);
		
		if(qtdEntregueAteAData == 0)
			return "Não existe(m) entrega(s) efetuada(s) anterior a data " + DateUtil.formataDiaMesAno(data) + ".";
		
		Integer qtdDevolvidaAteAData = solicitacaoEpiItemDevolucaoManager.findQtdDevolvidaByDataAndSolicitacaoItemId(data,solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);
		
		if((qtdDevolvidaAteAData + qtdASerDevolvida) > qtdEntregueAteAData){
			int maxASerDevolvido = qtdEntregueAteAData - qtdDevolvidaAteAData;
			if(maxASerDevolvido > 0)
				return "Não é possível inserir uma devolução nessa data ( " + DateUtil.formataDiaMesAno(data) + " ) maior que " + maxASerDevolvido + " Item(ns).";
			else
				return "Não é possível inserir uma devolução nessa data, pois já existe(m) devolução(ões) para a(s) entrega(s) efetuada(s) anterior a data " + DateUtil.formataDiaMesAno(data) + ".";
		}
		
		return null;
	}
	
	public void decideEdicaoSolicitacaoEpiItemEntrega(Long solicitacaoEpiItemId, SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega) {
		Collection<SolicitacaoEpiItemEntrega> entregas =  solicitacaoEpiItemEntregaManager.findBySolicitacaoEpiItem(solicitacaoEpiItemId);
		decideEdicaoSolicitacaoEpiItemEntrega(solicitacaoEpiItemId, entregas);
		for (SolicitacaoEpiItemEntrega solicitacaoEpiItemEntregaTemp : entregas) {
			if(solicitacaoEpiItemEntregaTemp.getId().equals(solicitacaoEpiItemEntrega.getId())){
				solicitacaoEpiItemEntrega.setItensEntregues(solicitacaoEpiItemEntregaTemp.isItensEntregues());
				break;
			}
		}
	}
	
	private void decideEdicaoSolicitacaoEpiItemEntrega(Long solicitacaoEpiItemId, Collection<SolicitacaoEpiItemEntrega> solicitacaoEpiItemEntregas) {
		Integer qtdDevolucaoAcumulado = solicitacaoEpiItemDevolucaoManager.getTotalDevolvido(solicitacaoEpiItemId, null);
		if(qtdDevolucaoAcumulado == null || qtdDevolucaoAcumulado == 0)
			return;
		
		for (SolicitacaoEpiItemEntrega solicitacaoEpiItemEntrega : solicitacaoEpiItemEntregas) {
			if(qtdDevolucaoAcumulado <= 0)
				break;
			
			solicitacaoEpiItemEntrega.setItensEntregues(true);
			qtdDevolucaoAcumulado = qtdDevolucaoAcumulado - solicitacaoEpiItemEntrega.getQtdEntregue();
		}
	}

	public void setSolicitacaoEpiItemEntregaManager(SolicitacaoEpiItemEntregaManager solicitacaoEpiItemEntregaManager) {
		this.solicitacaoEpiItemEntregaManager = solicitacaoEpiItemEntregaManager;
	}

	public void setEpiHistoricoManager(EpiHistoricoManager epiHistoricoManager) {
		this.epiHistoricoManager = epiHistoricoManager;
	}

	public void setSolicitacaoEpiItemDevolucaoManager(SolicitacaoEpiItemDevolucaoManager solicitacaoEpiItemDevolucaoManager) {
		this.solicitacaoEpiItemDevolucaoManager = solicitacaoEpiItemDevolucaoManager;
	}
}