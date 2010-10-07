package com.fortes.rh.business.avaliacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;

public interface PeriodoExperienciaManager extends GenericManager<PeriodoExperiencia>
{
	Collection<PeriodoExperiencia> findAllSelect(Long empresaId);
}
