package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.AreaInteresseFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.AreaInteresseListAction;

public class AreaInteresseListActionTest extends MockObjectTestCase
{
	private AreaInteresseListAction action;
	private Mock manager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new AreaInteresseListAction();
        manager = new Mock(AreaInteresseManager.class);
        action.setAreaInteresseManager((AreaInteresseManager) manager.proxy());
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
    	AreaInteresse ai1 = AreaInteresseFactory.getAreaInteresse();
    	ai1.setId(1L);

    	AreaInteresse ai2 = AreaInteresseFactory.getAreaInteresse();
    	ai2.setId(2L);

    	Collection<AreaInteresse> areaInteresses = new ArrayList<AreaInteresse>();
    	areaInteresses.add(ai1);
    	areaInteresses.add(ai2);

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(areaInteresses.size()));
    	manager.expects(once()).method("findToList").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(areaInteresses));

    	assertEquals("success", action.list());

    }

    public void testDelete() throws Exception
    {
    	AreaInteresse ai1 = AreaInteresseFactory.getAreaInteresse();
    	ai1.setId(1L);

    	action.setAreaInteresse(ai1);

    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals("success", action.delete());
    	assertFalse(action.getActionSuccess().isEmpty());
    }

    public void testGets() throws Exception
    {
    	action.getAreaInteresse();
    	action.getAreaInteresses();
    }
}
