package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public interface NivelCompetenciaManager extends GenericManager<NivelCompetencia>
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	void validaLimite(Long empresaId) throws Exception;

	Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long id);
}
