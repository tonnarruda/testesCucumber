package com.fortes.rh.dao.desenvolvimento;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;

public interface ParticipanteCursoLntDao extends GenericDao<ParticipanteCursoLnt> 
{
	Collection<ParticipanteCursoLnt> findByLnt(Long lntId);
	Collection<ParticipanteCursoLnt> findByLntIdAndAreasParticipantesIdsAndEmpresasIds(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds, String[] order);
	public Collection<ParticipanteCursoLnt> findByCursoLntId(Long cursoLntId);
}
