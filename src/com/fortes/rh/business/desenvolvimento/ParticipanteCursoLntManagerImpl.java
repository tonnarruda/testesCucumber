package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ParticipanteCursoLntDao;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;

@Component
public class ParticipanteCursoLntManagerImpl extends GenericManagerImpl<ParticipanteCursoLnt, ParticipanteCursoLntDao> implements ParticipanteCursoLntManager
{
	@Autowired
	public ParticipanteCursoLntManagerImpl(ParticipanteCursoLntDao dao) {
		setDao(dao);
	}
	
	public Collection<ParticipanteCursoLnt> findByLnt(Long lntId) throws Exception{
		return getDao().findByLnt(lntId);
	}

	public Collection<ParticipanteCursoLnt> findByLntIdAndAreasParticipantesIdsAndEmpresasIds(Long lntId, Long[] areasParticipantes, Long[] empresasIds, String[] order) {
		return getDao().findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lntId, areasParticipantes, empresasIds, order);
	}
	
	public Collection<ParticipanteCursoLnt> findByCursoLntId(Long cursoLntId) {
		return getDao().findByCursoLntId(cursoLntId);
	}

	public Map<Long,Collection<ParticipanteCursoLnt>> findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds) {
		
		Collection<ParticipanteCursoLnt> participantesCollection = getDao().findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lntId, areasParticipantesIds, empresasIds, new String[]{"c.nome"});
		Map<Long,Collection<ParticipanteCursoLnt>> participantesCursoLntMap = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		
		for (ParticipanteCursoLnt participanteCursoLnt : participantesCollection) {
			if(!participantesCursoLntMap.containsKey(participanteCursoLnt.getCursoLnt().getId()))
				participantesCursoLntMap.put(participanteCursoLnt.getCursoLnt().getId(), new ArrayList<ParticipanteCursoLnt>());

			participantesCursoLntMap.get(participanteCursoLnt.getCursoLnt().getId()).add(participanteCursoLnt);
		}
		
		
		return participantesCursoLntMap;
	}
}
