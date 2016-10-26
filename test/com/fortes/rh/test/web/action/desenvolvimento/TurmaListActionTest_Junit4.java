package com.fortes.rh.test.web.action.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.web.action.desenvolvimento.TurmaListAction;

public class TurmaListActionTest_Junit4 
{
	private TurmaListAction action = new TurmaListAction();
	private TurmaManager turmaManager;
	
	@Before
    public void setUp() throws Exception
    {
        turmaManager = mock(TurmaManager.class);
        action.setTurmaManager(turmaManager);
    }
	
	@Before
	public void inicializaCursoEturma(){
		Turma turma = TurmaFactory.getEntity(2L);
		Curso curso = CursoFactory.getEntity(1L); 
		
		action.setTurma(turma);
		action.setCurso(curso);
	}
	
	@Test
	public void testClonarColaboradores(){
		String[] turmasCheck = {"1","2"};
		action.setTurmasCheck(turmasCheck);
		
		action.clonarColaboradores();
		
		assertEquals("success", action.clonarColaboradores());
		assertEquals("Colaboradores clonados com sucesso para as turmas selecionadas.", action.getActionSuccess().iterator().next());
	}
	
	@Test
	public void testClonarColaboradoresException() throws Exception{
		String[] turmasCheck = {"1", "2"};
		Long[] turmasCheckLong = {1L, 2L};
		action.setTurmasCheck(turmasCheck);
		
		action.clonarColaboradores();
		doThrow(Exception.class).when(turmaManager).clonarColaboradores(action.getTurma().getId(), action.getCurso().getId(), turmasCheckLong);
		assertEquals("success", action.clonarColaboradores());
		assertEquals("Não foi possível clonar os colaboradores para as turmas selecionadas.", action.getActionErrors().iterator().next());
	}
}