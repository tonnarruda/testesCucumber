package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class RankingPerformancePeriodoExperiencia extends GenericManagerImpl<PeriodoExperiencia, PeriodoExperienciaDao> implements PeriodoExperienciaManager
{
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean orderDiasDesc)
	{
		return getDao().findAllSelect(empresaId, false);
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

	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<PeriodoExperiencia> periodos = getDao().findAllSelect(empresaId, false);
			return CheckListBoxUtil.populaCheckListBox(periodos, "getId", "getDiasDescricao");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}
}