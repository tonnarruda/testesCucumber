package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ParticipanteCursoLntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;

public class ParticipanteCursoLntManagerTest
{
	private ParticipanteCursoLntManagerImpl participanteCursoLntManagerImpl = new ParticipanteCursoLntManagerImpl();
	private ParticipanteCursoLntDao participanteCursoLntDao;
	
	@Before
	public void setUp() throws Exception
    {
		participanteCursoLntDao = mock(ParticipanteCursoLntDao.class);
		participanteCursoLntManagerImpl.setDao(participanteCursoLntDao);
    }
	
	@Test
	public void testFindByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		CursoLnt cursoLnt1 = CursoLntFactory.getEntity(1L);
		CursoLnt cursoLnt2 = CursoLntFactory.getEntity(2L);
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt1.setId(1L);
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt2.setId(2L);
		ParticipanteCursoLnt participanteCursoLnt3 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt2);
		participanteCursoLnt3.setId(3L);
		
		Collection<ParticipanteCursoLnt> participantesCollection =Arrays.asList(participanteCursoLnt1, participanteCursoLnt2, participanteCursoLnt3);
		
		when(participanteCursoLntDao.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), new Long[]{area.getId()}, new Long[]{}, null)).thenReturn(participantesCollection);
		
		Map<Long,Collection<ParticipanteCursoLnt>> retorno = participanteCursoLntManagerImpl.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lnt.getId(), new Long[]{area.getId()}, new Long[]{});

		assertEquals(2, retorno.size());
		assertEquals(2, retorno.get(cursoLnt1.getId()).size());
		assertEquals(1, retorno.get(cursoLnt2.getId()).size());
	}
	

}
