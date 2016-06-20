package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetencia;
import com.fortes.rh.model.captacao.NivelCompetencia;

public interface NivelCompetenciaDao extends GenericDao<NivelCompetencia> 
{
	Collection<NivelCompetencia> findAllSelect(Long empresaId, Long nivelCompetenciaHistoricoId, Date data);

	Collection<NivelCompetencia> findAllSelect(Long empresaId);

	Collection<ConfiguracaoNivelCompetencia> findByCargoOrEmpresa(Long cargoId, Long empresaId);

	int getOrdemMaxima(Long empresaId, Long nivelCompetenciaHistoricoId);

	boolean existePercentual(Long nivelCompetenciaId, Long empresaId, Double percentual);

	boolean existeNivelCompetenciaSemPercentual(Long empresaId);
	
	Double getOrdemMaximaByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);

	Integer getOrdemMaximaByAavaliacaoDesempenhoAndAvaliado(Long avaliacaoDesempenhoId, Long avaliadoId);
}
