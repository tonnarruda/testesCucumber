package com.fortes.rh.dao.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;

public interface CriterioAvaliacaoCompetenciaDao extends GenericDao<CriterioAvaliacaoCompetencia>
{
	public Collection<CriterioAvaliacaoCompetencia> findByCompetencia(Long competenciaId, Character tipoCompetencia);
	public void removeByCompetencia(Long competenciaId, Character tipoCompetencia, Long[] criteriosQuePermanecem);
	public boolean existeCriterioAvaliacaoCompetencia(Long empresaId);
}