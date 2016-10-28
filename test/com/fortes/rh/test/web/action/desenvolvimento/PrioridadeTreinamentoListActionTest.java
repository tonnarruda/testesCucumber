package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.desenvolvimento.PrioridadeTreinamentoManager;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.test.factory.desenvolvimento.PrioridadeTreinamentoFactory;
import com.fortes.rh.web.action.desenvolvimento.PrioridadeTreinamentoListAction;

public class PrioridadeTreinamentoListActionTest extends MockObjectTestCase
{
	private PrioridadeTreinamentoListAction action;
	private Mock prioridadeTreinamentoManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new PrioridadeTreinamentoListAction();

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
    
    public void testList() throws Exception
    {
    	Collection<PrioridadeTreinamento> prioridadeTreinamentos = new ArrayList<PrioridadeTreinamento>();
    	
    	prioridadeTreinamentoManager.expects(once()).method("getCount").will(returnValue(1));
    	prioridadeTreinamentoManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(prioridadeTreinamentos));
    	
    	assertEquals("success", action.list());
    	assertEquals(prioridadeTreinamentos, action.getPrioridadeTreinamentos());
    }

    public void testDelete() throws Exception
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);
    	
    	prioridadeTreinamentoManager.expects(once()).method("remove").with(eq(prioridadeTreinamento.getId()));

    	Collection<PrioridadeTreinamento> prioridadeTreinamentos = new ArrayList<PrioridadeTreinamento>();

    	prioridadeTreinamentoManager.expects(once()).method("getCount").will(returnValue(1));
    	prioridadeTreinamentoManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(prioridadeTreinamentos));
    	assertEquals("success", action.delete());
    }
    
    public void testDeleteException() throws Exception
    {
    	PrioridadeTreinamento prioridadeTreinamento = PrioridadeTreinamentoFactory.getEntity(1L);
    	action.setPrioridadeTreinamento(prioridadeTreinamento);
    	
    	prioridadeTreinamentoManager.expects(once()).method("remove").with(eq(prioridadeTreinamento.getId())).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(prioridadeTreinamento.getId(),""))));;
    	
    	Collection<PrioridadeTreinamento> prioridadeTreinamentos = new ArrayList<PrioridadeTreinamento>();
    	
    	prioridadeTreinamentoManager.expects(once()).method("getCount").will(returnValue(1));
    	prioridadeTreinamentoManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(prioridadeTreinamentos));
    	assertEquals("success", action.delete());
    }
      
    public void testGets() throws Exception
    {
    	action.setPrioridadeTreinamento(null);
    	assertNotNull(action.getPrioridadeTreinamento());
    }
}