package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.EngenheiroResponsavelListAction;

public class EngenheiroResponsavelListActionTest extends MockObjectTestCase
{
	private EngenheiroResponsavelListAction action;
	private Mock manager;


	protected void setUp() throws Exception
    {
        super.setUp();
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        action = new EngenheiroResponsavelListAction();
        manager = new Mock(EngenheiroResponsavelManager.class);

        action.setEngenheiroResponsavelManager((EngenheiroResponsavelManager) manager.proxy());
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    	manager = null;
        action = null;
        MockSecurityUtil.verifyRole = false;
        super.tearDown();
    }

    public void testExecute() throws Exception
    {
    	assertEquals(action.execute(), "success");
    }

	public void testDelete() throws Exception
	{
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setId(1L);
		action.setEngenheiroResponsavel(engenheiroResponsavel);

		EngenheiroResponsavel engTmp = new EngenheiroResponsavel();
		engTmp.setId(1L);
		Empresa empresa2 = new Empresa();
		empresa2.setId(1L);
		engTmp.setEmpresa(empresa2);

		manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(2));
		manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(null));
		manager.expects(once()).method("remove").with(ANYTHING);
		assertEquals("success", action.delete());
	}

    public void testList() throws Exception
    {
    	Collection<EngenheiroResponsavel> collEngenheiroResponsavel = new ArrayList<EngenheiroResponsavel>();

    	manager.expects(once()).method("getCount").with(ANYTHING, ANYTHING).will(returnValue(collEngenheiroResponsavel.size()));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(collEngenheiroResponsavel));

    	assertEquals("success", action.list());

    	assertEquals(action.getEngenheiroResponsavels(),collEngenheiroResponsavel);
    }

    public void testGetSet() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = null;
    	action.setEngenheiroResponsavel(engenheiroResponsavel);
    	assertTrue(action.getEngenheiroResponsavel() instanceof EngenheiroResponsavel);

    	engenheiroResponsavel = new EngenheiroResponsavel();
    	engenheiroResponsavel.setId(1L);
    	action.setEngenheiroResponsavel(engenheiroResponsavel);
    	assertEquals(action.getEngenheiroResponsavel(),engenheiroResponsavel);

    }
}