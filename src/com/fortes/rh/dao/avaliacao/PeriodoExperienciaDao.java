package com.fortes.rh.dao.avaliacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public interface PeriodoExperienciaDao extends GenericDao<PeriodoExperiencia> 
{
	Collection<PeriodoExperiencia> findAllSelect(Long empresaId);

	Integer findPeriodoAnterior(Long empresaId, Integer dias);

	Integer findPeriodoSugerido(Long empresaId, Integer dias);
	
}
