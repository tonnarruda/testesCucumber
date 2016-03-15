package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.ClinicaAutorizadaListAction;

public class ClinicaAutorizadaListActionTest extends MockObjectTestCase
{
	private ClinicaAutorizadaListAction action;
	private Mock manager;


	protected void setUp() throws Exception
    {
        super.setUp();
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        action = new ClinicaAutorizadaListAction();
        manager = new Mock(ClinicaAutorizadaManager.class);

        action.setClinicaAutorizadaManager((ClinicaAutorizadaManager) manager.proxy());
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
		ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setId(1L);
		action.setClinicaAutorizada(clinicaAutorizada);

		ClinicaAutorizada engTmp = new ClinicaAutorizada();
		engTmp.setId(1L);
		Empresa empresa2 = new Empresa();
		empresa2.setId(1L);
		engTmp.setEmpresa(empresa2);

		manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(2));
		manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(null));

		manager.expects(once()).method("remove").with(ANYTHING);
		assertEquals("success", action.delete());
	}

    public void testList() throws Exception
    {
    	Collection<ClinicaAutorizada> collClinicaAutorizada = new ArrayList<ClinicaAutorizada>();

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(collClinicaAutorizada.size()));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(collClinicaAutorizada));

    	assertEquals("success", action.list());

    	assertEquals(collClinicaAutorizada, action.getClinicaAutorizadas());
    }

    public void testGetSet() throws Exception
    {
    	ClinicaAutorizada clinicaAutorizada = null;
    	action.setClinicaAutorizada(clinicaAutorizada);
    	assertTrue(action.getClinicaAutorizada() instanceof ClinicaAutorizada);

    	clinicaAutorizada = new ClinicaAutorizada();
    	clinicaAutorizada.setId(1L);
    	action.setClinicaAutorizada(clinicaAutorizada);
    	assertEquals(action.getClinicaAutorizada(),clinicaAutorizada);

    }
}