package com.fortes.rh.test.web.action.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.web.action.desenvolvimento.CursoListAction;

public class CursoListActionTest_Junit4
{
	private CursoListAction action;
	private CursoManager cursoManager;

	@Before
    public void setUp() throws Exception
    {
        action = new CursoListAction();
        cursoManager = mock(CursoManager.class);
        action.setCursoManager(cursoManager);
    }

	@Test
    public void testClonar()
	{
    	Curso curso = CursoFactory.getEntity(1L);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);

    	action.setCurso(curso);
    	action.setEmpresaSistema(empresaSistema);
    	
		assertEquals("success", action.clonar());
		assertEquals("Curso clonado com sucesso.", action.getActionSuccess().iterator().next());
	}
	
	@Test
	public void testClonarException() throws Exception
	{
		Curso curso = CursoFactory.getEntity(1L);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);

    	action.setCurso(curso);
    	action.setEmpresaSistema(empresaSistema);
    	
    	doThrow(Exception.class).when(cursoManager).clonar(curso.getId(), empresaSistema.getId(), new Long[]{}, "");
    	
		action.clonar();
		assertEquals(1, action.getActionErrors().size());
		assertEquals("Não foi possível clonar o curso.",action.getActionErrors().iterator().next());
	}
}