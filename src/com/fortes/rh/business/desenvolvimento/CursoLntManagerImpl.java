package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.CursoLntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.SpringUtil;

public class CursoLntManagerImpl extends GenericManagerImpl<CursoLnt, CursoLntDao> implements CursoLntManager
{
	private ParticipanteCursoLntManager participanteCursoLntManager;
	
	public Collection<CursoLnt> findByLntId(Long lntId) {
		return getDao().findByLntId(lntId);
	}
	
	public Collection<CursoLnt> findByLntIdAndEmpresasIdsAndAreasParticipantesIds(Long lntId, Long[] areasParticipantesIds, Long[] empresasIds){

		Map<Long, Collection<ParticipanteCursoLnt>> participanteCursoLnts = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lntId, areasParticipantesIds, empresasIds);
		Collection<CursoLnt> cursLnts = findByLntId(lntId);

		for (CursoLnt cursoLnt : cursLnts) 
			cursoLnt.setParticipanteCursoLnts(participanteCursoLnts.get(cursoLnt.getId()));
		
		return cursLnts;
	}
	
	private Map<Long, Collection<Long>> montaMapParticipantesCursoLntRemovidos(String[] participantesRemovidos){
		
		Map<Long, Collection<Long>> mapParticipantesCursoLntRemovidos = new HashMap<Long, Collection<Long>>();
		
		if(participantesRemovidos != null && participantesRemovidos.length > 0){
			for (String participanteRemovido : participantesRemovidos) {
				if(participanteRemovido == null || participanteRemovido.split("_").length < 2 )
					continue;
				
				Long cursoLntId = new Long(participanteRemovido.split("_")[0]); 
				if(!mapParticipantesCursoLntRemovidos.containsKey(cursoLntId))
					mapParticipantesCursoLntRemovidos.put(cursoLntId, new ArrayList<Long>());
				
				Long participanteCursoLntId = new Long(participanteRemovido.split("_")[1]);
				
				mapParticipantesCursoLntRemovidos.get(cursoLntId).add(participanteCursoLntId);
			}
		}
		
		return mapParticipantesCursoLntRemovidos;
	} 
	
	public void saveOrUpdate(Lnt lnt, Collection<CursoLnt> cursosLnt, String[] participantesRemovidos, Long[] cursosRemovidos) throws Exception{
		
		Collection<ParticipanteCursoLnt> participanteCursoLntsASerSalvo = new ArrayList<ParticipanteCursoLnt>();
		Collection<Long> cursosLntIdExistentes = LongUtil.arrayLongToCollectionLong(new CollectionUtil<CursoLnt>().convertCollectionToArrayIds(findByLntId(lnt.getId())));
		
		if(cursosLnt != null){
			for (CursoLnt cursoLnt : cursosLnt){
				if(cursoLnt.getParticipanteCursoLnts()!= null){
					for (ParticipanteCursoLnt participanteCursoLnt : cursoLnt.getParticipanteCursoLnts()) 
						participanteCursoLnt.setCursoLnt(cursoLnt);
					
					participanteCursoLntsASerSalvo.addAll(cursoLnt.getParticipanteCursoLnts());
				}
				
				cursoLnt.setParticipanteCursoLnts(null);
				if(cursosLntIdExistentes.contains(cursoLnt.getId())){
					getDao().update(cursoLnt);
				}else{
					cursoLnt.setId(null);
					getDao().save(cursoLnt);
				}
			}
		}
		
		for(ParticipanteCursoLnt participanteCursoLnt : participanteCursoLntsASerSalvo){
			try {
				participanteCursoLntManager.save(participanteCursoLnt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Map<Long, Collection<Long>> mapParticipantesCursoLntRemovidos = montaMapParticipantesCursoLntRemovidos(participantesRemovidos);
		participantesASeremRemovidos(participantesRemovidos, mapParticipantesCursoLntRemovidos);
		
		if(cursosRemovidos != null && cursosRemovidos.length > 0)
			cursosASeremRemovidos(lnt.getId(), cursosRemovidos, mapParticipantesCursoLntRemovidos);
	}

	private void participantesASeremRemovidos(String[] participantesRemovidos, Map<Long, Collection<Long>> mapParticipantesCursoLntRemovidos) {
		if (participantesRemovidos != null && participantesRemovidos.length > 0){
			ColaboradorTurmaManager colaboradorTurmaManager = (ColaboradorTurmaManager) SpringUtil.getBean("colaboradorTurmaManager");
			
			Collection<Long> participantesCollection = new ArrayList<Long>();
			for(Collection<Long> participantesCursoLntCollection : mapParticipantesCursoLntRemovidos.values())
				participantesCollection.addAll(participantesCursoLntCollection);
			
			Long[] participantesArray = participantesCollection.toArray(new Long[participantesCollection.size()]);
			colaboradorTurmaManager.removeCursoLntByParticipantesCursoLnt(participantesArray);
			participanteCursoLntManager.remove(participantesArray);
		}
	}

	private void cursosASeremRemovidos(Long lntId, Long[] cursosRemovidos, Map<Long, Collection<Long>> mapParticipantesCursoLntRemovidos) {
		Map<Long, Collection<ParticipanteCursoLnt>> cursosLntMapExistentes = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lntId, null, null);
		
		Collection<Long> cursosLntsASerRemovidos = new CollectionUtil<Long>().convertArrayToCollection(cursosRemovidos);
		for (Long cursoLntIdASerRemovido : cursosLntsASerRemovidos) {
			boolean removerCursoLnt = true;
			
			if(cursosLntMapExistentes.containsKey(cursoLntIdASerRemovido)){
				for(ParticipanteCursoLnt participanteCursoLntExistente : cursosLntMapExistentes.get(cursoLntIdASerRemovido)){
					if(!mapParticipantesCursoLntRemovidos.get(cursoLntIdASerRemovido).contains(participanteCursoLntExistente.getId())){
						removerCursoLnt = false;
					}
				}
			}
			
			if(removerCursoLnt)
				getDao().remove(cursoLntIdASerRemovido);
		}
	}

	public Boolean existePerticipanteASerRelacionado(Long cursoLntId) {
		return getDao().existePerticipanteASerRelacionado(cursoLntId);
	}

	public void updateNomeNovoCurso(Long cursoId, String nomeNovoCurso) {
		getDao().updateNomeNovoCurso(cursoId, nomeNovoCurso);
	}
	
	public void removeCursoId(Long cursoId) {
		getDao().removeCursoId(cursoId);
	}
	
	public void setParticipanteCursoLntManager(ParticipanteCursoLntManager participanteCursoLntManager) {
		this.participanteCursoLntManager = participanteCursoLntManager;
	}

	public ParticipanteCursoLntManager getParticipanteCursoLntManager() {
		return this.participanteCursoLntManager;
	}
}
