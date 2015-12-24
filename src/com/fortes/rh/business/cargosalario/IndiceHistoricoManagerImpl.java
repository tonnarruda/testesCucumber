package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.util.SpringUtil;

public class IndiceHistoricoManagerImpl extends GenericManagerImpl<IndiceHistorico, IndiceHistoricoDao> implements IndiceHistoricoManager
{
	public Collection<IndiceHistorico> findAllSelect(Long indiceId)
	{
		return getDao().findAllSelect(indiceId);
	}

	public IndiceHistorico findByIdProjection(Long indiceHistoricoId)
	{
		return getDao().findByIdProjection(indiceHistoricoId);
	}

	public boolean verifyData(Long indiceHistoricoId, Date data, Long indiceId)
	{
		return getDao().verifyData(indiceHistoricoId, data, indiceId);
	}

	public Double findUltimoSalarioIndice(Long indiceId)
	{
		return getDao().findUltimoSalarioIndice(indiceId);
	}

	public Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo, Date dataDesligamento)
	{
		return getDao().findByPeriodo(indiceId, data, dataProximo, dataDesligamento);
	}

	@SuppressWarnings("deprecation")
	public boolean remove(Date data, Long indiceId) throws FortesException
	{
		if(!existeHistoricoAnteriorDaData(data, indiceId)){
			HistoricoColaboradorManager historicoColaboradorManager = (HistoricoColaboradorManager) SpringUtil.getBeanOld("historicoColaboradorManager");
			if(historicoColaboradorManager.existeDependenciaComHistoricoIndice(data, indiceId))
				throw new FortesException("O histórico deste índice não pode ser excluído, pois existe histórico de colaborador no RH que depende deste valor.");

			FaixaSalarialHistoricoManager faixaSalarialHistoricoManager = (FaixaSalarialHistoricoManager) SpringUtil.getBeanOld("faixaSalarialHistoricoManager");
			if(faixaSalarialHistoricoManager.existeDependenciaComHistoricoIndice(data, indiceId))
				throw new FortesException("O histórico deste índice não pode ser excluído, pois existe histórico de faixa salarial no RH que depende deste valor.");
		}
		
		return getDao().remove(data, indiceId);
	}
	
	public boolean existeHistoricoAnteriorOuIgualDaData(Date data, Long indiceId)
	{
		return getDao().existeHistoricoAnteriorDaData(data, indiceId, false);
	}
	
	public boolean existeHistoricoAnteriorDaData(Date data, Long indiceId)
	{
		return getDao().existeHistoricoAnteriorDaData(data, indiceId, true);
	}

	public void updateValor(Date data, Long indiceId, Double valor)
	{
		getDao().updateValor(data, indiceId, valor);
	}

	public void deleteByIndice(Long[] indiceIds) throws Exception {
		getDao().deleteByIndice(indiceIds);		
	}

	public Collection<IndiceHistorico> findByTabelaReajusteId(Long tabelaReajusteColaboradorId) 
	{
		return getDao().findByTabelaReajusteId(tabelaReajusteColaboradorId);
	}

	public Collection<IndiceHistorico> findByTabelaReajusteIdData(Long tabelaReajusteColaboradorId, Date data)
	{
		return getDao().findByTabelaReajusteIdData(tabelaReajusteColaboradorId, data);
	}
}