package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
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

	public Collection<CheckBox> populaCheckBoxDistinctDias(Long empresaId) {
		try
		{
			Collection<PeriodoExperiencia> periodos = getDao().findAllSelectDistinctDias(empresaId);
			Collection<CheckBox> listBox = new ArrayList<CheckBox>();
			for (PeriodoExperiencia periodo : periodos)
			{
				CheckBox checkBox = new CheckBox();
				checkBox.setId(periodo.getDias().longValue());
				checkBox.setNome(periodo.getDias() + " dias");
				listBox.add(checkBox);
			}
			
			return listBox;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}


	//TODO Sei la pq tem aqui e la na PeriodoExperienciaManagerImpl (Um dia vamos refatorar, qualquer informação do individuo que fez isso ligue para o 190)
	public Collection<FaixaPerformanceAvaliacaoDesempenho> agrupaFaixaAvaliacao(Collection<Colaborador> colaboradores, String[] percentualInicial, String[] percentualFinal) throws Exception  {
		return null;
	}
}