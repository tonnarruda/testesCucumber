package com.fortes.rh.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.CollectionUtil;

public class ParticipanteCursoLntDWR
{
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	
	public Collection<Colaborador> getParticipantesCursoLnt(Long cursoLntId)
	{
		if(cursoLntId == null)
			return null;

		Collection<Colaborador> colaboradorParticipantesRetorno = new ArrayList<Colaborador>();
		Collection<Colaborador> colaboradorParticipantesJaInseridos = new ArrayList<Colaborador>();
		Collection<ParticipanteCursoLnt> participantesCursoLnt = participanteCursoLntManager.findByCursoLntId(cursoLntId);
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByCursoLntId(cursoLntId);
		
		boolean colaboradorInserido = false;
		for (ParticipanteCursoLnt participanteCursoLnt : participantesCursoLnt) {
			colaboradorInserido = false;
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				if(participanteCursoLnt.getColaborador().getId().equals(colaboradorTurma.getColaborador().getId())){
					colaboradorInserido = true;
					break;
				}
			}

			if(colaboradorInserido){
				participanteCursoLnt.getColaborador().setIscritoNaTurma(true);
				if(!colaboradorParticipantesJaInseridos.contains(participanteCursoLnt.getColaborador()))
					colaboradorParticipantesJaInseridos.add(participanteCursoLnt.getColaborador());
			}else if(!colaboradorParticipantesRetorno.contains(participanteCursoLnt.getColaborador())){
				colaboradorParticipantesRetorno.add(participanteCursoLnt.getColaborador());
			}
		}

		colaboradorParticipantesRetorno = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradorParticipantesRetorno, "nome");
		colaboradorParticipantesJaInseridos = new CollectionUtil<Colaborador>().sortCollectionStringIgnoreCase(colaboradorParticipantesJaInseridos, "nome");
		colaboradorParticipantesRetorno.addAll(colaboradorParticipantesJaInseridos);
		
		return colaboradorParticipantesRetorno;
	}

	public void setParticipanteCursoLntManager(ParticipanteCursoLntManager participanteCursoLntManager) {
		this.participanteCursoLntManager = participanteCursoLntManager;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}
}
