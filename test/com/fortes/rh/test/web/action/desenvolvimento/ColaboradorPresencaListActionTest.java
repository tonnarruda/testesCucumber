package com.fortes.rh.test.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorPresencaFactory;
import com.fortes.rh.web.action.desenvolvimento.ColaboradorPresencaListAction;

public class ColaboradorPresencaListActionTest extends MockObjectTestCase
{
	private ColaboradorPresencaListAction action;
	private Mock colaboradorPresencaManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new ColaboradorPresencaListAction();

        colaboradorPresencaManager = new Mock(ColaboradorPresencaManager.class);
        action.setColaboradorPresencaManager((ColaboradorPresencaManager) colaboradorPresencaManager.proxy());
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
    
    public void testList() throws Exception
    {
    	Collection<ColaboradorPresenca> colaboradorPresencas = new ArrayList<ColaboradorPresenca>();
    	colaboradorPresencaManager.expects(once()).method("findAll").will(returnValue(colaboradorPresencas));
    	
    	assertEquals("success", action.list());
    	assertEquals(colaboradorPresencas, action.getColaboradorPresencas());
    }
    
    public void testDelete() throws Exception
    {
    	ColaboradorPresenca colaboradorPresenca = ColaboradorPresencaFactory.getEntity(1L);
    	action.setColaboradorPresenca(colaboradorPresenca);
    	
    	colaboradorPresencaManager.expects(once()).method("remove").with(eq(colaboradorPresenca.getId()));
    	
    	assertEquals("success", action.delete());
    }

    public void testGets() throws Exception
    {
    	action.setColaboradorPresenca(null);
    	assertNotNull(action.getColaboradorPresenca());

    }

}