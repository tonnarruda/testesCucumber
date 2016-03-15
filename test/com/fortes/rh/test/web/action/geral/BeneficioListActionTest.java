package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.BeneficioListAction;

public class BeneficioListActionTest extends MockObjectTestCase
{
	private BeneficioListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new BeneficioListAction();
        manager = new Mock(BeneficioManager.class);
        action.setBeneficioManager((BeneficioManager) manager.proxy());
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
    	Beneficio beneficio1 = BeneficioFactory.getEntity();
    	beneficio1.setId(1L);

    	Beneficio beneficio2 = BeneficioFactory.getEntity();
    	beneficio2.setId(2L);

    	Collection<Beneficio> Beneficios = new ArrayList<Beneficio>();
    	Beneficios.add(beneficio1);
    	Beneficios.add(beneficio2);

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(Beneficios.size()));
    	manager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(Beneficios));

    	String result = action.list();
		assertEquals(result, "success");

    	manager.verify();
    }

    public void testDelete() throws Exception
    {


    	Beneficio beneficio1 = BeneficioFactory.getEntity();
    	beneficio1.setId(1L);
    	action.setBeneficio(beneficio1);

    	Collection<Beneficio> beneficios = new ArrayList<Beneficio>();

    	manager.expects(atLeastOnce()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(beneficios.size()));
    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(atLeastOnce()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(beneficios));

    	assertEquals("success", action.delete());

    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(atLeastOnce()).method("remove").with(ANYTHING);

    	assertEquals("success", action.delete());

    	action.setBeneficioManager(null);

    	Exception exception = null;
    	try
    	{
    		action.delete();
    	}
    	catch (Exception e) {
    		exception = e;
		}

    	assertNotNull(exception);

    	manager.verify();
    }

    public void testGets() throws Exception
    {
    	action.getBeneficio();
    	action.getBeneficios();
    }
}
