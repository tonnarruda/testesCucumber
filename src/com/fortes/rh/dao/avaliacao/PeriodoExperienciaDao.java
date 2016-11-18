package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public interface PeriodoExperienciaDao extends GenericDao<PeriodoExperiencia> 
{
	Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean ordenarDiasDesc, Boolean ativo);
	Collection<PeriodoExperiencia> findAllSelectDistinctDias(Long empresaId);
	Collection<PeriodoExperiencia> findByIdsOrderDias(Long[] periodoExperienciaIds);
	Collection<PeriodoExperiencia> findAllAtivos();
	Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(Long empresaId, Long colaboradorId);
	Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodoDaAvaliacaoId(Long empresaId, Long avaliacaoId); 
}