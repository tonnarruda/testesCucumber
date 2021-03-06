package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.desenvolvimento.CursoListAction;

public class CursoListActionTest extends MockObjectTestCase
{
	private CursoListAction action;
	private Mock cursoManager;
	private Mock empresaManager;
	private Mock cursoLntManager;
	

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new CursoListAction();
        cursoManager = new Mock(CursoManager.class);
        empresaManager = new Mock(EmpresaManager.class);
        cursoLntManager = new Mock(CursoLntManager.class);
        
        action.setCursoManager((CursoManager) cursoManager.proxy());
        action.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        action.setCursoLntManager((CursoLntManager) cursoLntManager.proxy());

        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        cursoManager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
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
    	empresaManager.expects(once()).method("findEmpresasPermitidas").will(returnValue(new ArrayList<Empresa>()));
       	
    	assertEquals("success", action.list());
    	assertEquals(cursos, action.getCursos());
    }

    public void testDelete() throws Exception
    {
    	Curso curso = CursoFactory.getEntity(1L);
    	action.setCurso(curso);

    	cursoManager.expects(once()).method("verifyExists").with(ANYTHING, ANYTHING).will(returnValue(true));
    	cursoManager.expects(once()).method("remove").with(eq(curso.getId()));
    	cursoLntManager.expects(once()).method("removeCursoId").with(eq(curso.getId()));

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
}