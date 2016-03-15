package com.fortes.rh.test.web.action.geral;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.factory.geral.HistoricoBeneficioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.HistoricoBeneficioEditAction;

public class HistoricoBeneficioEditActionTest extends MockObjectTestCase
{
	private HistoricoBeneficioEditAction action;
	private Mock manager;
	private Mock beneficioManager;

	protected void setUp()
	{
		action = new HistoricoBeneficioEditAction();
		manager = new Mock(HistoricoBeneficioManager.class);
		beneficioManager = new Mock(BeneficioManager.class);
		action.setHistoricoBeneficioManager((HistoricoBeneficioManager) manager.proxy());
		action.setBeneficioManager((BeneficioManager) beneficioManager.proxy());
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

    public void testPrepareInsert() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);

    	action.setBeneficio(beneficio);

    	beneficioManager.expects(once()).method("findBeneficioById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	assertEquals(action.prepareInsert(), "success");

    	beneficioManager.expects(once()).method("findBeneficioById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	assertEquals(action.prepareInsert(), "error");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	action.setBeneficio(beneficio);

    	HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio.setId(1L);
    	historicoBeneficio.setBeneficio(beneficio);
    	action.setHistoricoBeneficio(historicoBeneficio);

    	beneficioManager.expects(once()).method("findBeneficioById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("findByHistoricoId").with(ANYTHING).will(returnValue(historicoBeneficio));

    	assertEquals(action.prepareUpdate(), "success");

    	beneficioManager.expects(once()).method("findBeneficioById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("findByHistoricoId").with(eq(historicoBeneficio.getId())).will(returnValue(historicoBeneficio));
    	assertEquals(action.prepareUpdate(), "error");
    }

    public void testInsert() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	action.setBeneficio(beneficio);

    	HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio.setId(1L);
    	historicoBeneficio.setBeneficio(beneficio);
    	action.setHistoricoBeneficio(historicoBeneficio);

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("save").with(eq(historicoBeneficio));

    	assertEquals(action.insert(), "success");

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("findByHistoricoId").with(ANYTHING).will(returnValue(historicoBeneficio));
    	beneficioManager.expects(once()).method("findBeneficioById").with(ANYTHING).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));

    	assertEquals(action.insert(), "input");

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("findByHistoricoId").with(ANYTHING).will(returnValue(historicoBeneficio));
    	beneficioManager.expects(once()).method("findBeneficioById").with(ANYTHING).will(returnValue(beneficio));
    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));

    	assertEquals(action.insert(), "input");
    }

    public void testUpdate() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	action.setBeneficio(beneficio);

    	HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio.setId(1L);
    	historicoBeneficio.setBeneficio(beneficio);
    	action.setHistoricoBeneficio(historicoBeneficio);

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	manager.expects(once()).method("update").with(eq(historicoBeneficio));

    	assertEquals(action.update(), "success");

//    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
//    	manager.expects(atLeastOnce()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
//    	manager.expects(once()).method("findByHistoricoId").with(ANYTHING).will(returnValue(historicoBeneficio));
//    	beneficioManager.expects(once()).method("findBeneficioById").with(ANYTHING).will(returnValue(beneficio));
//    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
//
//    	assertEquals(action.update(), "input");
//
//    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
//    	manager.expects(once()).method("findByHistoricoId").with(ANYTHING).will(returnValue(historicoBeneficio));
//    	beneficioManager.expects(once()).method("findBeneficioById").with(ANYTHING).will(returnValue(beneficio));
//    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
//
//    	assertEquals(action.update(), "input");
    }

    public void testGetsSets() throws Exception
    {
    	HistoricoBeneficio HistoricoBeneficio = HistoricoBeneficioFactory.getEntity();
    	HistoricoBeneficio.setId(1L);
    	action.setHistoricoBeneficio(HistoricoBeneficio);

    	action.getHistoricoBeneficio();
    	action.setHistoricoBeneficio(null);
    	action.getHistoricoBeneficio();

    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	action.setBeneficio(beneficio);

    	action.getBeneficio();
    	action.setBeneficio(null);
    	action.getBeneficio();

    }
}