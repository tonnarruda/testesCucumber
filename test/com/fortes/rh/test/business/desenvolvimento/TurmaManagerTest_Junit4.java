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

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.json.TurmaJson;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;

public class TurmaManagerTest_Junit4
{
	private TurmaManagerImpl turmaManager = new TurmaManagerImpl();
	private TurmaDao turmaDao;
	private ColaboradorTurmaManager colaboradorTurmaManager;

	@Before
	public void setUp() throws Exception
	{
		turmaDao = mock(TurmaDao.class);
		turmaManager.setDao(turmaDao);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		
		turmaManager.setDao(turmaDao);
		turmaManager.setColaboradorTurmaManager(colaboradorTurmaManager);
		
		
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
	
	@Test
	public void testGetTurmasByCursoNotTurmaId() {
		Long cursoId = 2L;
		Long notTurmaId = 35L;
		Collection<Turma> turmas = Arrays.asList(TurmaFactory.getEntity(), TurmaFactory.getEntity());
		when(turmaDao.getTurmasByCursoNotTurmaId(cursoId, notTurmaId)).thenReturn(turmas);
		Collection<Turma> retorno = turmaManager.getTurmasByCursoNotTurmaId(cursoId, notTurmaId);
		
		verify(turmaDao, times(1)).getTurmasByCursoNotTurmaId(eq(cursoId), eq(notTurmaId));
		assertEquals(turmas, retorno);
	}
	
	@Test
	public void clonarColaboradores() throws Exception {
		Long turmaId = 2L;
		Long cursoId = 3L;
		Long[] turmasCheck = new Long[]{1L};
		Collection<Colaborador> colaboradoresTurmaComId2 = Arrays.asList(ColaboradorFactory.getEntity(1L), ColaboradorFactory.getEntity(2L));
		Collection<Colaborador> colaboradoresJaInscritosTurmaComId1 = Arrays.asList(ColaboradorFactory.getEntity(1L));
		
		when(colaboradorTurmaManager.findColabodoresByTurmaId(eq(turmaId))).thenReturn(colaboradoresTurmaComId2);
		when(colaboradorTurmaManager.findColabodoresByTurmaId(eq(1L))).thenReturn(colaboradoresJaInscritosTurmaComId1);
		turmaManager.clonarColaboradores(turmaId, cursoId, turmasCheck);
		verify(colaboradorTurmaManager, times(1)).save(any(ColaboradorTurma.class));
	}
	
	@Test
	public void clonarColaboradoresSemColaboradoresInscritosNaTurmaDestino() throws Exception {
		Long turmaId = 2L;
		Long cursoId = 3L;
		Long[] turmasCheck = new Long[]{1L};
		Collection<Colaborador> colaboradoresTurmaComId2 = Arrays.asList(ColaboradorFactory.getEntity(1L), ColaboradorFactory.getEntity(2L));
		when(colaboradorTurmaManager.findColabodoresByTurmaId(eq(turmaId))).thenReturn(colaboradoresTurmaComId2);
		when(colaboradorTurmaManager.findColabodoresByTurmaId(eq(1L))).thenReturn(new ArrayList<Colaborador>());
		turmaManager.clonarColaboradores(turmaId, cursoId, turmasCheck);
		verify(colaboradorTurmaManager, times(2)).save(any(ColaboradorTurma.class));
	}
}