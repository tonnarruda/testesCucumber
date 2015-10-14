package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public interface NivelCompetenciaDao extends GenericDao<NivelCompetencia> 
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long cargoId, Long empresaId);

	int getOrdemMaxima(Long empresaId);

	boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual);

	boolean existeNivelCompetenciaSemPercentual(Long empresaId);
}
