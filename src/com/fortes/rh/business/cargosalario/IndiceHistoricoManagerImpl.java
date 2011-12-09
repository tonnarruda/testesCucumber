package com.fortes.rh.business.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.IndiceHistoricoDao;
import com.fortes.rh.model.cargosalario.IndiceHistorico;

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

	public Collection<IndiceHistorico> findByPeriodo(Long indiceId, Date data, Date dataProximo)
	{
		return getDao().findByPeriodo(indiceId, data, dataProximo);
	}

	public boolean remove(Date data, Long indiceId)
	{
		return getDao().remove(data, indiceId);
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
}