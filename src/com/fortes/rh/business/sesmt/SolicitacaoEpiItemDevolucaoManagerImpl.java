package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.sesmt.SolicitacaoEpiItemDevolucaoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.sesmt.SolicitacaoEpi;
import com.fortes.rh.model.sesmt.SolicitacaoEpiItemDevolucao;
import com.fortes.rh.util.CollectionUtil;

public class SolicitacaoEpiItemDevolucaoManagerImpl extends GenericManagerImpl<SolicitacaoEpiItemDevolucao, SolicitacaoEpiItemDevolucaoDao> implements SolicitacaoEpiItemDevolucaoManager{
	
	public int getTotalDevolvido(Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		return getDao().getTotalDevolvido(solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);
	}

	public Collection<SolicitacaoEpiItemDevolucao> findSolicEpiItemDevolucaoBySolicitacaoEpiItem(Long solicitacaoEpiItemId) {
		return getDao().findBySolicitacaoEpiItem(solicitacaoEpiItemId);
	}

	public Integer findQtdDevolvidaByDataAndSolicitacaoItemId(Date data, Long solicitacaoEpiItemId, Long solicitacaoEpiItemDevolucaoId) {
		return getDao().findQtdDevolvidaByDataAndSolicitacaoItemId(data, solicitacaoEpiItemId, solicitacaoEpiItemDevolucaoId);
	}
	
	private Map<Long, Integer> findQtdDevolvidaBySolicitacaoItemId(Long[] solicitacaoEpiItensId){
		Map<Long, Integer> solicitacaoEpiItemDevolucoesMap = new HashMap<Long, Integer>();
		
		Collection<SolicitacaoEpiItemDevolucao> solicitacaoEpiItemDevolucoes =  getDao().findQtdDevolvidaBySolicitacaoItemIds(solicitacaoEpiItensId);
		for (SolicitacaoEpiItemDevolucao solicitacaoEpiItemDevolucao : solicitacaoEpiItemDevolucoes) {
			if(solicitacaoEpiItemDevolucao.getSolicitacaoEpiItem() != null)
				solicitacaoEpiItemDevolucoesMap.put(solicitacaoEpiItemDevolucao.getSolicitacaoEpiItem().getId(), solicitacaoEpiItemDevolucao.getQtdDevolvida());
		}
		
		return solicitacaoEpiItemDevolucoesMap;
	}
	
	public Collection<SolicitacaoEpi> removeItensDevolvidos(Collection<SolicitacaoEpi> solicitacaoEpis) throws ColecaoVaziaException{
		Collection<SolicitacaoEpi> solicitacaoEpisRetorno = new ArrayList<>();
		Long[] solicitacaoEpiItensId = new CollectionUtil<SolicitacaoEpi>().convertCollectionToArrayIds(solicitacaoEpis, "getSolicitacaoEpiItemId");
		Map<Long, Integer> solicitacaoesEpiItemDevolvidosMap = findQtdDevolvidaBySolicitacaoItemId(solicitacaoEpiItensId);

		Long solicitacaoEpiItemId = 0L;
		Integer qtdTotalDevolvida = 0;
		Integer qtdAcumuladoDeEntregue = 0;

		for (SolicitacaoEpi solicitacaoEpi : solicitacaoEpis) {
			if(solicitacaoesEpiItemDevolvidosMap.containsKey(solicitacaoEpi.getSolicitacaoEpiItemId())){
				if(!solicitacaoEpiItemId.equals(solicitacaoEpi.getSolicitacaoEpiItemId())){
					solicitacaoEpiItemId = solicitacaoEpi.getSolicitacaoEpiItemId();
					qtdTotalDevolvida = solicitacaoesEpiItemDevolvidosMap.get(solicitacaoEpi.getSolicitacaoEpiItemId());
					qtdAcumuladoDeEntregue = 0;
				}

				qtdAcumuladoDeEntregue += solicitacaoEpi.getQtdEpiEntregue();

				if(qtdAcumuladoDeEntregue > qtdTotalDevolvida)
					solicitacaoEpisRetorno.add(solicitacaoEpi);
			}else
				solicitacaoEpisRetorno.add(solicitacaoEpi);
		}

		if (solicitacaoEpisRetorno == null || solicitacaoEpisRetorno.isEmpty())
			throw new ColecaoVaziaException("Não existem EPIs com prazo a vencer para os filtros informados.");
		
		return solicitacaoEpisRetorno;
	}
}
