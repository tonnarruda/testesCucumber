package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.util.LongUtil;

@Component
public class LntDWR {

	private ParticipanteCursoLntManager participanteCursoLntManager;
	private LntManager lntManager;

	public String checaParticipantesNaLnt(Long lntId, Long[] areasIds){
		Collection<ParticipanteCursoLnt>  participantesCursosLnt = participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lntId, null, null, new String[]{});
		Map<Long, String> areaMap = new HashMap<Long, String>();
		Collection<Long> areasIdsCollection = LongUtil.arrayLongToCollectionLong(areasIds);
		
		for (ParticipanteCursoLnt participanteCursoLnt : participantesCursosLnt) {
			if(!areaMap.containsKey(participanteCursoLnt.getAreaOrganizacional().getId()) && !areasIdsCollection.contains(participanteCursoLnt.getAreaOrganizacional().getId()))
				areaMap.put(participanteCursoLnt.getAreaOrganizacional().getId(), participanteCursoLnt.getEmpresaNome() + " - " + participanteCursoLnt.getAreaOrganizacional().getNome());
		}
		
		String retorno = null;
		if(areaMap.size() > 0){
			retorno = "Existem participantes alocados nas áreas organizacionais abaixo:</br></br>";
			for (String empresaMaisArea : areaMap.values()) {
				retorno += empresaMaisArea + "</br>";
			}

			retorno += "</br>Deseja realmente efetuar essas alterações? </br>";
			retorno += "Caso opte por efetuar a alteração, os participantes das áreas organizacionais citadas acima serão removidos da LNT.</br>";
		}
		
		return retorno;
	}
	
	public Collection<Lnt> findLntsColaborador(Long cursoId, Long colaboradorId){
		Collection<Lnt> lnts = lntManager.findPossiveisLntsColaborador(cursoId, colaboradorId);
		Long lntIdQueParticipa = lntManager.findLntColaboradorParticpa(cursoId, colaboradorId);
		
		if(lntIdQueParticipa != null)
			for (Lnt lnt : lnts) 
				lnt.setSelected(lnt.getId().equals(lntIdQueParticipa));
		
		return lnts;
	}

	public void setParticipanteCursoLntManager(ParticipanteCursoLntManager participanteCursoLntManager) {
		this.participanteCursoLntManager = participanteCursoLntManager;
	}

	public void setLntManager(LntManager lntManager) {
		this.lntManager = lntManager;
	}
}
