package com.fortes.rh.business.desenvolvimento;

import java.util.Collection;
import java.util.Map;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;

public interface ParticipanteCursoLntManager extends GenericManager<ParticipanteCursoLnt>
{
	public Collection<ParticipanteCursoLnt> findByLnt(Long lntId) throws Exception;
	public Collection<ParticipanteCursoLnt> findByLntIdAndAreasParticipantesIdsAndEmpresasIds(Long lntId, Long[] areasParticipantes, Long[] empresasIds, String[] order);
	public Collection<ParticipanteCursoLnt> findByCursoLntId(Long cursoLntId);
	public Map<Long,Collection<ParticipanteCursoLnt>> findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds);
}
