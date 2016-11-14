package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.TurmaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.json.TurmaJson;

public class TurmaManagerTest_Junit4
{
	TurmaManagerImpl turmaManager = new TurmaManagerImpl();
	TurmaDao turmaDao;

	@Before
	public void setUp() throws Exception
	{
		turmaDao = mock(TurmaDao.class);
		turmaManager.setDao(turmaDao);
	}
	
	@Test
	public void testGetTurmasJson() {
		String baseCnpj = "01234";
		Long turmaId = 1L;
		char realizada = 'S';
		
		Collection<TurmaJson>turmasJson = Arrays.asList(new TurmaJson());
		when(turmaDao.getTurmasJson(baseCnpj, turmaId, realizada)).thenReturn(turmasJson);
		assertEquals(turmasJson, turmaManager.getTurmasJson(baseCnpj, turmaId, realizada));
	}
	
}