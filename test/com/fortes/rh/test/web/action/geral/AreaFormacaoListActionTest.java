package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.AreaFormacaoFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.AreaFormacaoListAction;

public class AreaFormacaoListActionTest extends MockObjectTestCase
{
	private AreaFormacaoListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new AreaFormacaoListAction();
        manager = new Mock(AreaFormacaoManager.class);
        action.setAreaFormacaoManager((AreaFormacaoManager) manager.proxy());
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
    }

    protected void tearDown() throws Exception
    {
        manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

    public void testList() throws Exception
    {
    	AreaFormacao ai1 = AreaFormacaoFactory.getEntity();
    	ai1.setNome("teste1");
    	ai1.setId(1L);

    	AreaFormacao ai2 = AreaFormacaoFactory.getEntity();
    	ai2.setId(2L);
    	ai2.setNome("teste2");

    	Collection<AreaFormacao> areaFormacaos = new ArrayList<AreaFormacao>();
    	areaFormacaos.add(ai1);
    	areaFormacaos.add(ai2);
    	
    	AreaFormacao area1 = new AreaFormacao();
    	area1.setNome("test");
    	    	
    	action.setAreaFormacaos(areaFormacaos);
    	action.setAreaFormacao(area1);

    	manager.expects(once()).method("getCount").will(returnValue(areaFormacaos.size()));
    	manager.expects(once()).method("findByFiltro").with(eq(1),eq(15),eq(area1)).will(returnValue(areaFormacaos));

    	assertEquals("success", action.list());
    }

    public void testDelete() throws Exception
    {
    	AreaFormacao ai1 = AreaFormacaoFactory.getEntity();
    	ai1.setId(1L);

    	action.setAreaFormacao(ai1);

    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionMessages().isEmpty());
    }

    public void testGets() throws Exception
    {
    	action.getAreaFormacao();
    	action.getAreaFormacaos();
    }
}

