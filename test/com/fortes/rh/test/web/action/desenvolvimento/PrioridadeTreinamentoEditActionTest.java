package com.fortes.rh.test.web.action.desenvolvimento;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.test.factory.desenvolvimento.PrioridadeTreinamentoFactory;
import com.fortes.rh.web.action.desenvolvimento.PrioridadeTreinamentoEditAction;

public class PrioridadeTreinamentoEditActionTest extends MockObjectTestCase
{
	private PrioridadeTreinamentoEditAction action;
	private Mock prioridadeTreinamentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new PrioridadeTreinamentoEditAction();
        prioridadeTreinamentoManager = new Mock(PrioridadeTreinamentoManager.class);
        action.setPrioridadeTreinamentoManager((PrioridadeTreinamentoManager) prioridadeTreinamentoManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        prioridadeTreinamentoManager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
        
    public void testPrepareInsert() throws Exception
    {
    	assertEquals("success", action.prepareInsert());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);
    	prioridadeTreinamentoManager.expects(once()).method("findById").with(eq(prioridadeTreinamento.getId())).will(returnValue(prioridadeTreinamento));

    	assertEquals("success", action.prepareUpdate());
    	assertEquals(prioridadeTreinamento, action.getPrioridadeTreinamento());
    }
    
    public void testInsert() throws Exception
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);

    	prioridadeTreinamentoManager.expects(once()).method("save").with(eq(prioridadeTreinamento));
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);
    	
    	prioridadeTreinamentoManager.expects(once()).method("update").with(eq(prioridadeTreinamento));
    	assertEquals("success", action.update());
    }
    
    public void testGets() throws Exception
    {
    	action.setPrioridadeTreinamento(null);
    	assertNotNull(action.getModel());
    	
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);
    	assertEquals(prioridadeTreinamento, action.getPrioridadeTreinamento());
    }

}