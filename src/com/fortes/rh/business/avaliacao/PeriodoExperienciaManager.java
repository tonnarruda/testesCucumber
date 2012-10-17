package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.web.tags.CheckBox;

public interface PeriodoExperienciaManager extends GenericManager<PeriodoExperiencia> 
{
	Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean orderDiasDesc);
	String findRodapeDiasDoPeriodoDeExperiencia(Collection<PeriodoExperiencia> periodoExperiencias);
	Collection<CheckBox> populaCheckBox(Long empresaId);
	Collection<CheckBox> populaCheckBoxDistinctDias(Long empresaId);
	Collection<FaixaPerformanceAvaliacaoDesempenho> agrupaFaixaAvaliacao(Collection<Colaborador> colaboradores, String[] percentualInicial, String[] percentualFinal) throws Exception;
	Collection<PeriodoExperiencia> findByIdsOrderDias(Long[] PeriodoExperienciaIds);
}