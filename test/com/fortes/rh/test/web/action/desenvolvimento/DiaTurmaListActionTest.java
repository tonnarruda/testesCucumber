package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.web.action.desenvolvimento.DiaTurmaListAction;

public class DiaTurmaListActionTest extends MockObjectTestCase
{
	private DiaTurmaListAction action;
	private Mock diaTurmaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new DiaTurmaListAction();

        diaTurmaManager = new Mock(DiaTurmaManager.class);
        action.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        diaTurmaManager = null;
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testList() throws Exception
    {
    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	diaTurmaManager.expects(once()).method("findAll").will(returnValue(diaTurmas));
    	assertEquals("success", action.list());
    	assertEquals(diaTurmas, action.getDiaTurmas());
    }

    public void testDelete() throws Exception
    {
    	DiaTurma diaTurma = DiaTurmaFactory.getEntity(1L);
    	action.setDiaTurma(diaTurma);
    	
    	diaTurmaManager.expects(once()).method("remove").with(eq(diaTurma.getId()));
    	assertEquals("success", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.setDiaTurma(null);
    	assertNotNull(action.getDiaTurma());
    }
}