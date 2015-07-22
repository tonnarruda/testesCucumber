package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.CursoListAction;

public class CursoListActionTest extends MockObjectTestCase
{
	private CursoListAction action;
	private Mock cursoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CursoListAction();
        cursoManager = new Mock(CursoManager.class);
        action.setCursoManager((CursoManager) cursoManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        cursoManager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	Collection<Curso> cursos = new ArrayList<Curso>();

    	cursoManager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(1));
    	cursoManager.expects(once()).method("findByFiltro").with(new Constraint[]{ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(cursos));
    	assertEquals("success", action.list());
    	assertEquals(cursos, action.getCursos());
    }

    public void testDelete() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	cursoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(true));
    	cursoManager.expects(once()).method("remove").with(eq(curso.getId()));

    	assertEquals("success", action.delete());
    }

    public void testDeleteEmpresaErrada() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	cursoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(false));

    	assertEquals("success", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.setCurso(null);
    	assertNotNull(action.getCurso());
    	action.setNomeCursoBusca("Treinamento");
    	assertEquals("Treinamento",action.getNomeCursoBusca());
    }
    
    public void testClonar()
	{
    	Curso curso = CursoFactory.getEntity(1L);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);

    	action.setCurso(curso);
    	action.setEmpresaSistema(empresaSistema);
    	
    	cursoManager.expects(once()).method("clonar").with(eq(curso.getId()), eq(empresaSistema.getId()), ANYTHING).isVoid();
		assertEquals("success", action.clonar());
		assertEquals("Curso clonado com sucesso.", action.getActionSuccess());
	}
	public void testClonarException()
	{
		Curso curso = CursoFactory.getEntity(1L);
    	Empresa empresaSistema = EmpresaFactory.getEmpresa(1L);

    	action.setCurso(curso);
    	action.setEmpresaSistema(empresaSistema);
    	
    	cursoManager.expects(once()).method("clonar").with(eq(curso.getId()), eq(empresaSistema.getId()), ANYTHING).will(throwException(new Exception()));
		action.clonar();
		assertEquals(1, action.getActionErrors().size());
		assertEquals("Não foi possível clonar o curso.",action.getActionErrors().iterator().next());
	}
}