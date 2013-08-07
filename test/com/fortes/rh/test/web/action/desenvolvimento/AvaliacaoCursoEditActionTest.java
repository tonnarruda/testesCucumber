package com.fortes.rh.test.web.action.desenvolvimento;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.web.action.desenvolvimento.AvaliacaoCursoEditAction;

public class AvaliacaoCursoEditActionTest extends MockObjectTestCase
{
	private AvaliacaoCursoEditAction action;
	private Mock avaliacaoCursoManager;
	private Mock avaliacaoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new AvaliacaoCursoEditAction();
        action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
        avaliacaoCursoManager = new Mock(AvaliacaoCursoManager.class);
        action.setAvaliacaoCursoManager((AvaliacaoCursoManager) avaliacaoCursoManager.proxy());
        avaliacaoManager = new Mock(AvaliacaoManager.class);
        action.setAvaliacaoManager((AvaliacaoManager) avaliacaoManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        avaliacaoCursoManager = null;
        avaliacaoManager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	action.setAvaliacaoCurso(avaliacaoCurso);
    	
    	avaliacaoCursoManager.expects(once()).method("findById").with(eq(avaliacaoCurso.getId())).will(returnValue(avaliacaoCurso));
    	avaliacaoManager.expects(once()).method("findToList").withAnyArguments();
    	assertEquals("success", action.prepareUpdate());
    }
    
    public void testPrepareInsert() throws Exception
    {
    	avaliacaoManager.expects(once()).method("findToList").withAnyArguments();
    	
    	assertEquals("success", action.prepareInsert());
    }
    
    public void testInsert() throws Exception
    {
    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	action.setAvaliacaoCurso(avaliacaoCurso);

    	avaliacaoCursoManager.expects(once()).method("save").with(eq(avaliacaoCurso));
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	action.setAvaliacaoCurso(avaliacaoCurso);

    	avaliacaoCursoManager.expects(once()).method("update").with(eq(avaliacaoCurso));
    	assertEquals("success", action.update());
    }

    public void testGets() throws Exception
    {
    	AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity(1L);
    	action.setAvaliacaoCurso(avaliacaoCurso);
    	assertEquals(avaliacaoCurso, action.getModel());
    	
    	action.setAvaliacaoCurso(null);
    	assertNotNull(action.getAvaliacaoCurso());
    	
    	assertNotNull(action.getTipos());
    }

}