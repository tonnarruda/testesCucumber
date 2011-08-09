package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public interface NivelCompetenciaManager extends GenericManager<NivelCompetencia>
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	void validaLimite(Long empresaId) throws Exception;

	Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long id, Long empresaId);

}
