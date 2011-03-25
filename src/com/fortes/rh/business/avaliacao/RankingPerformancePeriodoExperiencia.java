package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public class RankingPerformancePeriodoExperiencia extends GenericManagerImpl<PeriodoExperiencia, PeriodoExperienciaDao> implements PeriodoExperienciaManager
{
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean orderDiasDesc)
	{
		return getDao().findAllSelect(empresaId, false);
	}

	public Integer findPeriodoAnterior(Long empresaId, Integer dias) {
		return getDao().findPeriodoAnterior(empresaId, dias);
	}

	public Integer findPeriodoSugerido(Long empresaId, Integer dias) {
		return getDao().findPeriodoSugerido(empresaId, dias);
	}

	public String findRodapeDiasDoPeriodoDeExperiencia(Collection<PeriodoExperiencia> periodoExperiencias) {
		String rodapeRelatorioPeriodoExperiencia = ""; 
		boolean decisao = false;
		
		if(periodoExperiencias.size() > 0  )
			rodapeRelatorioPeriodoExperiencia = "Periodos de Experiências "; 
		
		for (PeriodoExperiencia periodoExperiencia : periodoExperiencias) {
		
			if (decisao)
				rodapeRelatorioPeriodoExperiencia += ", ";
			
			rodapeRelatorioPeriodoExperiencia += periodoExperiencia.getDias().toString();
			decisao = true;
		}
		
		
		return rodapeRelatorioPeriodoExperiencia;
	}
}