package com.fortes.rh.test.factory.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;

public class ParticipanteCursoLntFactory
{
	public static ParticipanteCursoLnt getEntity()
	{
		ParticipanteCursoLnt participanteCursoLnt  = new ParticipanteCursoLnt();
		participanteCursoLnt.setId(null);
		return participanteCursoLnt;
	}

	public static ParticipanteCursoLnt getEntity(Long id)
	{
		ParticipanteCursoLnt participanteCursoLnt = getEntity();
		participanteCursoLnt.setId(id);

		return participanteCursoLnt;
	}

	public static Collection<ParticipanteCursoLnt> getCollection()
	{
		Collection<ParticipanteCursoLnt> participanteCursoLnts = new ArrayList<ParticipanteCursoLnt>();
		participanteCursoLnts.add(getEntity());

		return participanteCursoLnts;
	}
	
	public static Collection<ParticipanteCursoLnt> getCollection(Long id)
	{
		Collection<ParticipanteCursoLnt> participanteCursoLnts = new ArrayList<ParticipanteCursoLnt>();
		participanteCursoLnts.add(getEntity(id));
		
		return participanteCursoLnts;
	}

	public static ParticipanteCursoLnt getEntity(Colaborador colaborador, AreaOrganizacional areaOrganizacional, CursoLnt cursoLnt)
	{
		ParticipanteCursoLnt participanteCursoLnt = getEntity();
		participanteCursoLnt.setColaborador(colaborador);
		participanteCursoLnt.setAreaOrganizacional(areaOrganizacional);
		participanteCursoLnt.setCursoLnt(cursoLnt);

		return participanteCursoLnt;
	}
}
