package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

public class IndiceHistoricoManagerImpl extends GenericManagerImpl<IndiceHistorico, IndiceHistoricoDao> implements IndiceHistoricoManager
{
	private HistoricoColaboradorManager historicoColaboradorManager;
	
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

	public Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo)
	{
		return getDao().findByPeriodo(indiceId, data, dataProximo);
	}

	public boolean remove(Date data, Long indiceId) throws FortesException
	{
		Date[] doisPrimeirosHistoricos = findDoisPrimeirosHistoricos(data, indiceId);

		if(doisPrimeirosHistoricos.length == 1 && data.compareTo(doisPrimeirosHistoricos[0]) == 0)
			throw new FortesException("Este histórico não pode ser excluído, pois é o único deste índice.");
		else if(doisPrimeirosHistoricos.length > 1 && data.compareTo(doisPrimeirosHistoricos[1]) < 0){
			//	testar se hist col/faixa depende deste hist de indice
			Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorManager.findDependenciasComHistoricoIndice(data, indiceId);
		}
		
		return getDao().remove(data, indiceId);
	}
	
	public Date[] findDoisPrimeirosHistoricos(Date data, Long indiceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean existsAnteriorByDataIndice(Date data, Long indiceId)
	{
		return getDao().existsAnteriorByDataIndice(data, indiceId);
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

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager) 
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}
}