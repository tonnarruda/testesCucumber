package com.fortes.rh.business.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public interface ConfiguracaoNivelCompetenciaFaixaSalarialManager extends GenericManager<ConfiguracaoNivelCompetenciaFaixaSalarial>
{
	void deleteByFaixaSalarial(Long[] faixaIds);
	void deleteDependenciasByFaixaSalarial(Long[] faixaIds);
	Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> findProximasConfiguracoesAposData(Long faixaSalarialId, Date data);
	boolean existByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
	ConfiguracaoNivelCompetenciaFaixaSalarial findByFaixaSalarialIdAndData(Long faixaSalarialId, Date data);
}
