package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.web.action.desenvolvimento.AvaliacaoCursoListAction;

public class AvaliacaoCursoListActionTest extends MockObjectTestCase
{
	private AvaliacaoCursoListAction action;
	private Mock avaliacaoCursoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new AvaliacaoCursoListAction();
        avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
        action.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        avaliacaoCursoManager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testList() throws Exception
    {
    	Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();

    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	avaliacaoCurso.setTitulo("teste");
    	action.setAvaliacaoCurso(avaliacaoCurso);
    	
    	avaliacaoCursoManager.expects(once()).method("buscaFiltro").with(eq(avaliacaoCurso.getTitulo())).will(returnValue(avaliacaoCursos));
    	
    	assertEquals("success", action.list());
    	assertEquals(avaliacaoCursos, action.getAvaliacaoCursos());
    }

    public void testDelete() throws Exception
    {
    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	action.setAvaliacaoCurso(avaliacaoCurso);
    	avaliacaoCursoManager.expects(once()).method("remove").with(eq(avaliacaoCurso.getId()));
    	assertEquals("success", action.delete());
    }
  
    public void testGets() throws Exception
    {
    	action.setAvaliacaoCurso(null);
    	assertNotNull(action.getAvaliacaoCurso());
    	assertNotNull(action.getTipos());
    }
}