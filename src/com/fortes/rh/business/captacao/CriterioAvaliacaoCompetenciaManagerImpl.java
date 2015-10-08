/* Autor: Robertson Freitas
 * Data: 16/06/2006
 * Requisito: RFA33 */
package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.CriterioAvaliacaoCompetenciaDao;
import com.fortes.rh.model.captacao.CriterioAvaliacaoCompetencia;
import com.fortes.rh.util.CollectionUtil;

public class CriterioAvaliacaoCompetenciaManagerImpl extends GenericManagerImpl<CriterioAvaliacaoCompetencia, CriterioAvaliacaoCompetenciaDao> implements CriterioAvaliacaoCompetenciaManager
{
	public Collection<CriterioAvaliacaoCompetencia> findByCompetencia(Long competenciaId, Character tipoCompetencia)
	{
		return getDao().findByCompetencia(competenciaId, tipoCompetencia);
	}
	
	public void removeByCompetencia(Long competenciaId, Character tipoCompetencia, Collection<CriterioAvaliacaoCompetencia> criteriosQuePermanecem) {
		getDao().removeByCompetencia(competenciaId, tipoCompetencia, new CollectionUtil<CriterioAvaliacaoCompetencia>().convertCollectionToArrayIds(criteriosQuePermanecem) );
	}
}