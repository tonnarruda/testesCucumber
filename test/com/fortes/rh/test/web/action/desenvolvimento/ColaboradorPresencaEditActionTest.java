package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorPresencaEditAction;

public class ColaboradorPresencaEditActionTest extends MockObjectTestCase
{
	private ColaboradorPresencaEditAction action;
	private Mock colaboradorPresencaManager;
	private Mock diaTurmaManager;
	private Mock colaboradorTurmaManager;
	
    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorPresencaEditAction();

        colaboradorTurmaManager = new Mock(ColaboradorTurmaManager.class);
        action.setColaboradorTurmaManager((ColaboradorTurmaManager) colaboradorTurmaManager.proxy());

        colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());
        
        diaTurmaManager = new Mock(DiaTurmaManager.class);
        action.setDiaTurmaManager((DiaTurmaManager) diaTurmaManager.proxy());
    }

    protected void tearDown() throws Exception
    {
        action = null;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }
    
    public void testPrepareInsert() throws Exception
    {
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	
    	colaboradorTurmaManager.expects(once()).method("findAll").will(returnValue(colaboradorTurmas));
    	diaTurmaManager.expects(once()).method("findAll").will(returnValue(diaTurmas));
    	
    	assertEquals(action.prepareInsert(), "success");
    	assertEquals(colaboradorTurmas, action.getColaboradorTurmas());
    	assertEquals(diaTurmas, action.getDiaTurmas());
    }
    
    public void testPrepareUpdate() throws Exception
    {
    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
    	action.setColaboradorPresenca(colaboradorPresenca);
    	
    	Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
    	Collection<DiaTurma> diaTurmas = new ArrayList<DiaTurma>();
    	
    	colaboradorPresencaManager.expects(once()).method("findById").will(returnValue(colaboradorPresenca));
    	colaboradorTurmaManager.expects(once()).method("findAll").will(returnValue(colaboradorTurmas));
    	diaTurmaManager.expects(once()).method("findAll").will(returnValue(diaTurmas));
    	
    	assertEquals(action.prepareUpdate(), "success");
    	assertEquals(colaboradorPresenca, action.getColaboradorPresenca());
    }
    
    public void testInsert() throws Exception
    {
    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
    	action.setColaboradorPresenca(colaboradorPresenca);

    	colaboradorPresencaManager.expects(once()).method("save").with(eq(colaboradorPresenca)).will(returnValue(colaboradorPresenca));
    	
    	assertEquals(action.insert(), "success");
    }
    
    public void testUpdate() throws Exception
    {
    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
    	action.setColaboradorPresenca(colaboradorPresenca);
    	
    	colaboradorPresencaManager.expects(once()).method("update").with(eq(colaboradorPresenca));
    	
    	assertEquals(action.update(), "success");
    }
   
    public void testGets() throws Exception
    {
    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
    	action.setColaboradorPresenca(colaboradorPresenca);
    	
    	assertEquals(colaboradorPresenca, action.getModel());

    	action.setColaboradorPresenca(null);
    	assertNotNull(action.getColaboradorPresenca());
    	
    	action.setColaboradorTurmas(null);
    	action.setDiaTurmas(null);
    }

}