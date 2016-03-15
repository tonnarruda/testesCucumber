package com.fortes.rh.test.web.action.sesmt;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.EngenheiroResponsavelEditAction;

public class EngenheiroResponsavelEditActionTest extends MockObjectTestCase
{
	private EngenheiroResponsavelEditAction action;
	private Mock manager;

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(EngenheiroResponsavelManager.class);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		action = new EngenheiroResponsavelEditAction();
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

	public void testPrepareInsert() throws Exception
	{
		EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
		engenheiroResponsavel.setId(1L);

		action.setEngenheiroResponsavel(engenheiroResponsavel);

		manager.expects(once()).method("findByIdProjection").with(eq(engenheiroResponsavel.getId())).will(returnValue(engenheiroResponsavel));

		assertEquals(action.prepareInsert(), "success");
		assertEquals(action.getEngenheiroResponsavel(), engenheiroResponsavel);
	}

    public void testPrepareUpdate() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
    	engenheiroResponsavel.setId(1L);
    	engenheiroResponsavel.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

    	manager.expects(atLeastOnce()).method("findByIdProjection").with(eq(engenheiroResponsavel.getId())).will(returnValue(engenheiroResponsavel));
    	assertEquals(action.prepareUpdate(), "success");

    	manager.expects(atLeastOnce()).method("findByIdProjection").with(eq(engenheiroResponsavel.getId())).will(returnValue(null));
    	assertEquals(action.prepareUpdate(), "error");
    	assertNotNull(action.getActionErrors());

    }

    public void testInsert() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
    	engenheiroResponsavel.setId(1L);
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

		manager.expects(once()).method("save").with(eq(engenheiroResponsavel)).will(returnValue(engenheiroResponsavel));

    	assertEquals(action.insert(), "success");
    	assertEquals(action.getEngenheiroResponsavel(), engenheiroResponsavel);

    }

    public void testUpdate() throws Exception
    {
    	EngenheiroResponsavel engenheiroResponsavel = new EngenheiroResponsavel();
    	engenheiroResponsavel.setId(1L);
    	engenheiroResponsavel.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setEngenheiroResponsavel(engenheiroResponsavel);

    	manager.expects(once()).method("update").with(eq(engenheiroResponsavel));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getEngenheiroResponsavel(), engenheiroResponsavel);

    }

    public void testGetSet() throws Exception
    {
    	action.setEngenheiroResponsavel(null);
        action.getEngenheiroResponsavel();
    }
}