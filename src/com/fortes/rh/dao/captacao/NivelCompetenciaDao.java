package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetenciaFaixaSalarial;

public interface NivelCompetenciaDao extends GenericDao<NivelCompetencia> 
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	Collection<NivelCompetenciaFaixaSalarial> findByCargoOrEmpresa(Long cargoId, Long empresaId);
}
