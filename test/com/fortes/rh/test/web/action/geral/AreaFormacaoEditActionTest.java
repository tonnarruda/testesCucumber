package com.fortes.rh.test.web.action.geral;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.test.factory.geral.AreaFormacaoFactory;
import com.fortes.rh.web.action.geral.AreaFormacaoEditAction;

public class AreaFormacaoEditActionTest extends MockObjectTestCase
{
	private AreaFormacaoEditAction action;
	private Mock manager;

	protected void setUp()
	{
		action = new AreaFormacaoEditAction();
		manager = new Mock(AreaFormacaoManager.class);
		action.setAreaFormacaoManager((AreaFormacaoManager) manager.proxy());
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testPrepareInsert() throws Exception
    {
    	assertEquals("success", action.prepareInsert());

    }

    public void testPrepareUpdate() throws Exception
    {
    	AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();
    	areaFormacao.setId(1L);

    	action.setAreaFormacao(areaFormacao);

    	manager.expects(once()).method("findById").with(eq(areaFormacao.getId())).will(returnValue(areaFormacao));

    	assertEquals("success", action.prepareUpdate());

    }

    public void testInsert() throws Exception
    {
    	AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();

    	action.setAreaFormacao(areaFormacao);

    	manager.expects(once()).method("save").with(ANYTHING);

    	assertEquals("success", action.insert());
    }

    public void testUpdate() throws Exception
    {
    	AreaFormacao areaFormacao = AreaFormacaoFactory.getEntity();
    	areaFormacao.setId(1L);

    	action.setAreaFormacao(areaFormacao);

    	manager.expects(once()).method("update").with(ANYTHING);

    	assertEquals("success", action.update());
    }

    public void testGetsSets() throws Exception
    {
    	action.getAreaFormacao();
    	action.getModel();
    }
}