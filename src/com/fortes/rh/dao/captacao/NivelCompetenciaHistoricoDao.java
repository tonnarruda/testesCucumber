package com.fortes.rh.dao.captacao;

import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.NivelCompetenciaHistorico;

public interface NivelCompetenciaHistoricoDao extends GenericDao<NivelCompetenciaHistorico> 
{
	public boolean existeDependenciaComCompetenciasDaFaixaSalarial(Long nivelConfiguracaoHistoricoId);
	public Long findByData(Date date, Long empresaId);
}
