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

	public Collection<FaturamentoMensal> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Object[]> findByPeriodo(Date inicio, Date fim, Long empresaId) 
	{
		FaturamentoMensal ultimoFaturamento = getDao().findAtual(inicio);
		Collection<FaturamentoMensal> faturamentos = getDao().findByPeriodo(inicio, fim, empresaId);
		Collection<Object[]> graficoEvolucaoFaturamento = new ArrayList<Object[]>();

		Date mesAno = inicio;
		double faturamentoAtual = (ultimoFaturamento != null && ultimoFaturamento.getValor() != null) ? ultimoFaturamento.getValor() : 0;
		
		while (mesAno.before(fim))
		{
			for (FaturamentoMensal faturamento : faturamentos)
				if (DateUtil.equalsMesAno(mesAno, faturamento.getMesAno()))
					faturamentoAtual = faturamento.getValor();
					
			graficoEvolucaoFaturamento.add(new Object[]{DateUtil.getUltimoDiaMes(mesAno).getTime(), faturamentoAtual});			
			mesAno = DateUtil.incrementaMes(mesAno, 1);
		}

		return graficoEvolucaoFaturamento;
	}

	public Double somaByPeriodo(Date dataIni, Date dataFim, Long empresaId) 
	{
		return getDao().somaByPeriodo(DateUtil.getInicioMesData(dataIni), DateUtil.getUltimoDiaMes(dataFim), empresaId);
	}
}
