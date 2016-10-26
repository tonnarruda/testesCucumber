package com.fortes.rh.test.web.dwr;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.web.dwr.TurmaDWR;

public class TurmaDWRTest_Junit4
{
	private TurmaDWR turmaDWR;
	private TurmaManager turmaManager;

	@Before
	public void setUp() throws Exception
	{
		turmaDWR =  new TurmaDWR();

		turmaManager = mock(TurmaManager.class);
		turmaDWR.setTurmaManager(turmaManager);
	}
	
	@Test
	public void testGetTurmasByCursoNotTurmaId() throws Exception
	{
		Long cursoId = 2L;
		Long notTurmaId = 5L;
		
		Turma turma1 = TurmaFactory.getEntity(1L, "Turma 1");
		Turma turma2 = TurmaFactory.getEntity(2L, "Turma 2");
		
		Collection<Turma> turmas = Arrays.asList(turma1, turma2);
		when(turmaManager.getTurmasByCursoNotTurmaId(cursoId, notTurmaId)).thenReturn(turmas);
		Map retorno = turmaDWR.getTurmasByCursoNotTurmaId(cursoId, notTurmaId); 
		assertEquals(turmas.size(), retorno.size());
	}
}
