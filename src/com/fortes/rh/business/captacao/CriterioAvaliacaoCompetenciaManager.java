package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;

public interface CriterioAvaliacaoCompetenciaManager extends GenericManager<CriterioAvaliacaoCompetencia>
{
	public Collection<CriterioAvaliacaoCompetencia> findByCompetenciaAndCNCFId(Long competenciaId, Long configuracaonivelcompetenciafaixasalarialId, Character tipoCompetencia);
	public void removeByCompetencia(Long competenciaId, Character tipoCompetencia, Collection<CriterioAvaliacaoCompetencia> criteriosQuePermaneceram);
	public boolean existeCriterioAvaliacaoCompetencia(Long empresaId);
	public Collection<CriterioAvaliacaoCompetencia> findByCompetencia(Long competenciaId, Character tipoCompetencia);
}