package com.fortes.rh.business.captacao;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public interface ConfiguracaoNivelCompetenciaFaixaSalarialManager extends GenericManager<ConfiguracaoNivelCompetenciaFaixaSalarial>
{
	void deleteByFaixaSalarial(Long[] faixaIds);
	void deleteDependenciasByFaixaSalarial(Long[] faixaIds);
}
