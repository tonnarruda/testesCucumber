package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.web.dwr.ParticipanteCursoLntDWR;

public class ParticipanteCursoLntDWRTest {

	private ParticipanteCursoLntDWR participanteCursoLntDWR;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	
	
	@Before
	public void setUp() throws Exception
	{
		participanteCursoLntDWR = new ParticipanteCursoLntDWR();

		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		participanteCursoLntDWR.setParticipanteCursoLntManager(participanteCursoLntManager);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		participanteCursoLntDWR.setColaboradorTurmaManager(colaboradorTurmaManager);
	}
	
	@Test
	public void testGetParticipantesCursoLnt() throws Exception{
		Long cursoLntId = 1L;
		
		Colaborador colab1 = ColaboradorFactory.getEntity(1L, "A");
		Colaborador colab2 = ColaboradorFactory.getEntity(2L, "B");
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(colab1, null, null);
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(colab2, null, null);
		Collection<ParticipanteCursoLnt> participantesCursoLnt = Arrays.asList(participanteCursoLnt1, participanteCursoLnt2);

		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(colab1, null, null);
		
		when(participanteCursoLntManager.findByCursoLntId(cursoLntId)).thenReturn(participantesCursoLnt);
		when(colaboradorTurmaManager.findByCursoLntId(cursoLntId)).thenReturn(Arrays.asList(colaboradorTurma));
		
		Collection<Colaborador> colaboradorParticipantesRetorno = participanteCursoLntDWR.getParticipantesCursoLnt(cursoLntId);
		
		assertEquals(2, colaboradorParticipantesRetorno.size());
		assertEquals(colab2.getNome(), ((Colaborador)colaboradorParticipantesRetorno.toArray()[0]).getNome());
	}
	
	
}
