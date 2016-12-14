package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManagerImpl;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.dao.desenvolvimento.CursoLntDao;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class CursoLntManagerTest
{
	private CursoLntManagerImpl cursoLntManagerImpl = new CursoLntManagerImpl();
	private CursoLntDao cursoLntDao;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	
	@Before
	public void setUp() throws Exception
    {
		cursoLntDao = mock(CursoLntDao.class);
		cursoLntManagerImpl.setDao(cursoLntDao);
		
		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		cursoLntManagerImpl.setParticipanteCursoLntManager(participanteCursoLntManager);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		MockSpringUtilJUnit4.mocks.put("colaboradorTurmaManager", colaboradorTurmaManager);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }
	
	@Test
	public void testfindByLntIdAndEmpresasIdsAndAreasParticipantesIds()
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
		
		Map<Long,Collection<ParticipanteCursoLnt>> participantesMap = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		participantesMap.put(cursoLnt1.getId(), Arrays.asList(participanteCursoLnt1, participanteCursoLnt2));
		participantesMap.put(cursoLnt2.getId(), Arrays.asList(participanteCursoLnt2));
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lnt.getId(), new Long[]{area.getId()}, new Long[]{})).thenReturn(participantesMap);
		when(cursoLntDao.findByLntId(lnt.getId())).thenReturn(Arrays.asList(cursoLnt1, cursoLnt2));
		
		Collection<CursoLnt> retorno = cursoLntManagerImpl.findByLntIdAndEmpresasIdsAndAreasParticipantesIds(lnt.getId(), new Long[]{area.getId()}, new Long[]{});
		
		assertEquals(2, retorno.size());
		assertEquals(2, ((CursoLnt)retorno.toArray()[0]).getParticipanteCursoLnts().size());
		assertEquals(1, ((CursoLnt)retorno.toArray()[1]).getParticipanteCursoLnts().size());
	}
	
	@Test
	public void testSaveOrUpdateInserir() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		CursoLnt cursoLnt1 = CursoLntFactory.getEntity(1L);
		CursoLnt cursoLnt2 = CursoLntFactory.getEntity();
		
		Collection<CursoLnt> cursosLnt = Arrays.asList(cursoLnt1, cursoLnt2);
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt1.setId(1L);
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt2.setId(2L);
		ParticipanteCursoLnt participanteCursoLnt3 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt2);
		participanteCursoLnt3.setId(3L);
		
		cursoLnt1.setParticipanteCursoLnts(Arrays.asList(participanteCursoLnt1, participanteCursoLnt2));
		cursoLnt2.setParticipanteCursoLnts(Arrays.asList(participanteCursoLnt3));
		
		Map<Long,Collection<ParticipanteCursoLnt>> participantesMap = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		participantesMap.put(cursoLnt1.getId(), Arrays.asList(participanteCursoLnt1, participanteCursoLnt2));
		
		when(cursoLntDao.findByLntId(lnt.getId())).thenReturn(Arrays.asList(cursoLnt1));
		
		cursoLntManagerImpl.saveOrUpdate(lnt, cursosLnt, null, null);
		
		verify(cursoLntDao, times(1)).update(eq(cursoLnt1));
		verify(cursoLntDao, times(1)).save(eq(cursoLnt2));
		verify(participanteCursoLntManager, times(3)).save(any(ParticipanteCursoLnt.class));
	}
	
	@Test
	public void testSaveOrUpdateInserirERemover() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		
		CursoLnt cursoLnt1 = CursoLntFactory.getEntity(1L);
		CursoLnt cursoLnt2 = CursoLntFactory.getEntity();
		CursoLnt cursoLntRemovido1 = CursoLntFactory.getEntity(2L);
		CursoLnt cursoLntRemovido2 = CursoLntFactory.getEntity(3L);
		Collection<CursoLnt> cursosLnt = Arrays.asList(cursoLnt1, cursoLnt2);
		
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt1.setId(1L);
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt1);
		participanteCursoLnt2.setId(2L);
		ParticipanteCursoLnt participanteCursoLnt3 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLnt2);
		participanteCursoLnt3.setId(3L);
		ParticipanteCursoLnt participanteCursoLntRemovido1 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLntRemovido1);
		participanteCursoLntRemovido1.setId(4L);
		ParticipanteCursoLnt participanteCursoLntRemovido2 = ParticipanteCursoLntFactory.getEntity(null, area, cursoLntRemovido1);
		participanteCursoLntRemovido2.setId(5L);
		
		cursoLnt1.setParticipanteCursoLnts(Arrays.asList(participanteCursoLnt1, participanteCursoLnt2));
		cursoLnt2.setParticipanteCursoLnts(Arrays.asList(participanteCursoLnt3));
		
		Map<Long,Collection<ParticipanteCursoLnt>> participantesMap = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		participantesMap.put(cursoLnt1.getId(), Arrays.asList(participanteCursoLnt1, participanteCursoLnt2));
		participantesMap.put(cursoLntRemovido1.getId(), Arrays.asList(participanteCursoLntRemovido1, participanteCursoLnt2));
		participantesMap.put(cursoLntRemovido2.getId(), new ArrayList<ParticipanteCursoLnt>());
		
		when(cursoLntDao.findByLntId(lnt.getId())).thenReturn(Arrays.asList(cursoLnt1));
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIdsMap(lnt.getId(), null, null)).thenReturn(participantesMap);
		
		cursoLntManagerImpl.saveOrUpdate(lnt, cursosLnt, new String[]{cursoLntRemovido1.getId() + "_" + participanteCursoLntRemovido1.getId()}, new Long[]{cursoLntRemovido1.getId(), cursoLntRemovido2.getId()});
		
		verify(cursoLntDao, times(1)).update(eq(cursoLnt1));
		verify(cursoLntDao, times(1)).save(eq(cursoLnt2));
		verify(participanteCursoLntManager, times(3)).save(any(ParticipanteCursoLnt.class));
		verify(participanteCursoLntManager, times(1)).remove(new Long[]{participanteCursoLntRemovido1.getId()});
		verify(cursoLntDao, times(1)).remove(cursoLntRemovido2.getId());
	}
}
