package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public interface NivelCompetenciaFaixaSalarialDao extends GenericDao<NivelCompetenciaFaixaSalarial> 
{
	void deleteConfiguracaoByFaixa(Long faixaSalarialId);

	Collection<NivelCompetenciaFaixaSalarial> findByFaixa(Long faixaSalarialId);
}
