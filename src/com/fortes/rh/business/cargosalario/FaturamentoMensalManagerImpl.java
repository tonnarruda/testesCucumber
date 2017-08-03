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
		FaturamentoMensal faturamentoInicial = getDao().findAtual(inicio, empresaId);
		Collection<FaturamentoMensal> faturamentos = getDao().findByPeriodo(inicio, fim, empresaId);
		Collection<Object[]> graficoEvolucaoFaturamento = new ArrayList<Object[]>();

		Date mesAno=DateUtil.criarDataMesAno(inicio);
		fim=DateUtil.criarDataMesAno(fim);
		
		double faturamentoAtual = (faturamentoInicial != null && faturamentoInicial.getValor() != null) ? faturamentoInicial.getValor() : 0;
		
		while (!mesAno.after(fim))
		{
			for (FaturamentoMensal faturamento : faturamentos){
				if (DateUtil.equalsMesAno(mesAno, faturamento.getMesAno())){
					faturamentoAtual = faturamento.getValor();
					break;
				}
				else
					faturamentoAtual = 0;			
			}
			graficoEvolucaoFaturamento.add(new Object[]{mesAno.getTime(), faturamentoAtual});			
			mesAno = DateUtil.incrementaMes(mesAno, 1);
		}

		return graficoEvolucaoFaturamento;
	}

	public Double somaByPeriodo(Date dataIni, Date dataFim, Long[] empresaIds) 
	{
		return getDao().somaByPeriodo(DateUtil.getInicioMesData(dataIni), DateUtil.getUltimoDiaMes(dataFim), empresaIds);
	}
}
