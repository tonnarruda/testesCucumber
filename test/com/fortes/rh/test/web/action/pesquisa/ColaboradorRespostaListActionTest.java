package com.fortes.rh.test.web.action.pesquisa;

import org.jmock.MockObjectTestCase;

import com.fortes.rh.web.action.pesquisa.ColaboradorRespostaListAction;

public class ColaboradorRespostaListActionTest extends MockObjectTestCase
{
	private ColaboradorRespostaListAction colaboradorRespostaListAction;

    protected void setUp() throws Exception
    {
        super.setUp();
        colaboradorRespostaListAction = new ColaboradorRespostaListAction();
    }

    protected void tearDown() throws Exception
    {
    	colaboradorRespostaListAction = null;

        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals("success", colaboradorRespostaListAction.execute());
    }

    public void testList() throws Exception
    {


    }

}
