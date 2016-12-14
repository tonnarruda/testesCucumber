package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.web.dwr.LntDWR;

public class lntDWRTest
{
	private LntDWR lntDWR;
	private LntManager lntManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;

	
	@Before
	public void setUp() throws Exception
	{
		lntDWR = new LntDWR();

		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		lntDWR.setParticipanteCursoLntManager(participanteCursoLntManager);
		
		lntManager = mock(LntManager.class);
		lntDWR.setLntManager(lntManager);
	}
	
	@Test
	public void testChecaParticipantesNaLnt() throws Exception{

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		
		Lnt lnt = LntFactory.getEntity(1L);
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(1L);
		participanteCursoLnt.setAreaOrganizacional(areaOrganizacional);
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), null, null, new String[]{})).thenReturn(Arrays.asList(participanteCursoLnt));
		
		assertEquals("Existem participantes alocados nas áreas organizacionais abaixo:</br></br> - nome da area organizacional</br></br>Deseja realmente efetuar essas alterações? </br>Caso opte por efetuar a alteração, os participantes das áreas organizacionais citadas acima serão removidos da LNT.</br>", lntDWR.checaParticipantesNaLnt(lnt.getId(), new Long[]{}));
	}
	
	@Test
	public void testFindLntsColaborador() throws Exception{
		Long cursoId = 1L;
		Long colaboradorId = 2L;
		
		Lnt lnt = LntFactory.getEntity(1L);
		Collection<Lnt> lnts = Arrays.asList(lnt);
		
		when(lntManager.findPossiveisLntsColaborador(cursoId, colaboradorId)).thenReturn(lnts);
		when(lntManager.findLntColaboradorParticpa(cursoId, colaboradorId)).thenReturn(lnt.getId());
		
		Collection<Lnt> retorno = lntDWR.findLntsColaborador(cursoId, colaboradorId);
		
		assertEquals(1, retorno.size());
		assertTrue(((Lnt) retorno.toArray()[0]).isSelected());
	}
}
