package com.fortes.rh.business.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.cargosalario.FaturamentoMensalDao;
import com.fortes.rh.model.cargosalario.FaturamentoMensal;
import com.fortes.rh.util.DateUtil;

public class FaturamentoMensalManagerImpl extends GenericManagerImpl<FaturamentoMensal, FaturamentoMensalDao> implements FaturamentoMensalManager
{

	public Collection<FaturamentoMensal> findAllSelect(Long empresaId) {
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Object[]> findByPeriodo(Date inicio, Date fim, Long empresaId) 
	{
		Collection<FaturamentoMensal> faturamentos = getDao().findByPeriodo(inicio, fim, empresaId);
		Collection<Object[]> graficoEvolucaoFaturamento = new ArrayList<Object[]>();

		for (FaturamentoMensal faturamento : faturamentos)
			graficoEvolucaoFaturamento.add(new Object[]{DateUtil.getUltimoDiaMes(faturamento.getMesAno()).getTime(), faturamento.getValor()});			

		return graficoEvolucaoFaturamento;
	}
}
