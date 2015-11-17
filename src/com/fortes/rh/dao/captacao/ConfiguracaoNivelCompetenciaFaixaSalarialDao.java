package com.fortes.rh.dao.captacao;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaFaixaSalarial;

public interface ConfiguracaoNivelCompetenciaFaixaSalarialDao extends GenericDao<ConfiguracaoNivelCompetenciaFaixaSalarial> 
{
	void deleteByFaixaSalarial(Long[] faixaIds);
	Collection<ConfiguracaoNivelCompetenciaFaixaSalarial> findProximasConfiguracoesAposData(Long faixaSalarialId, Date dataConfiguracaoExcluir);
	boolean existByNivelCompetenciaHistoricoId(Long nivelCompetenciaHistoricoId);
}
