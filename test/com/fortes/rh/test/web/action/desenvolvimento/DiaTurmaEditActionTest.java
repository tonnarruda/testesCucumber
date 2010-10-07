package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.test.factory.desenvolvimento.DiaTurmaFactory;
import com.fortes.rh.web.action.desenvolvimento.DiaTurmaEditAction;

public class DiaTurmaEditActionTest extends MockObjectTestCase
{
	private DiaTurmaEditAction action;
	private Mock diaTurmaManager;
	private Mock turmaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new DiaTurmaEditAction();
        diaTurmaManager = new Mock(DiaTurmaManager.class);
        action.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());

        turmaManager = new Mock(TurmaManager.class);
        action.setTurmaManager((TurmaManager) turmaManager.proxy());
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
        
    public void testPrepareInsert() throws Exception
    {
    	Collection<Turma> turmas = new ArrayList<Turma>();
    	turmaManager.expects(once()).method("findAll").will(returnValue(turmas));
    	assertEquals("success", action.prepareInsert());
    	assertEquals(turmas, action.getTurmas());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	DiaTurma diaTurma = DiaTurmaFactory.getEntity(1L);
    	action.setDiaTurma(diaTurma);
    	Collection<Turma> turmas = new ArrayList<Turma>();
    	
    	turmaManager.expects(once()).method("findAll").will(returnValue(turmas));
    	diaTurmaManager.expects(once()).method("findById").with(eq(diaTurma.getId())).will(returnValue(diaTurma));

    	assertEquals("success", action.prepareUpdate());
    	assertEquals(turmas, action.getTurmas());
    	assertEquals(diaTurma, action.getDiaTurma());
    }
    
    public void testInsert() throws Exception
    {
    	DiaTurma diaTurma = DiaTurmaFactory.getEntity(1L);
    	action.setDiaTurma(diaTurma);

    	diaTurmaManager.expects(once()).method("save").with(eq(diaTurma));
    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	DiaTurma diaTurma = DiaTurmaFactory.getEntity(1L);
    	action.setDiaTurma(diaTurma);

    	diaTurmaManager.expects(once()).method("update").with(eq(diaTurma));
    	assertEquals("success", action.update());
    }
    
    public void testGets() throws Exception
    {
    	action.setDiaTurma(null);
    	assertNotNull(action.getDiaTurma());
    	
    	DiaTurma diaTurma = DiaTurmaFactory.getEntity(1L);
    	action.setDiaTurma(diaTurma);
    	assertEquals(diaTurma, action.getModel());
    }

}