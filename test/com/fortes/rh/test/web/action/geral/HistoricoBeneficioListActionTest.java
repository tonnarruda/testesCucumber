package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

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
import com.fortes.rh.web.action.geral.HistoricoBeneficioListAction;

public class HistoricoBeneficioListActionTest extends MockObjectTestCase
{
	private HistoricoBeneficioListAction action;
	private Mock manager;
	private Mock beneficioManager;

    protected void setUp() throws Exception
    {
        super.setUp();
        action = new HistoricoBeneficioListAction();
        manager = new Mock(HistoricoBeneficioManager.class);
        beneficioManager = new Mock(BeneficioManager.class);
        action.setHistoricoBeneficioManager((HistoricoBeneficioManager) manager.proxy());
        action.setBeneficioManager((BeneficioManager) beneficioManager.proxy());
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
    	HistoricoBeneficio historicoBeneficio1 = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio1.setId(1L);

    	HistoricoBeneficio historicoBeneficio2 = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio2.setId(2L);

    	Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();
    	historicoBeneficios.add(historicoBeneficio1);
    	historicoBeneficios.add(historicoBeneficio2);

    	manager.expects(once()).method("findAll").will(returnValue(historicoBeneficios));

    	assertEquals(action.list(), "success");
    }

    public void testDelete() throws Exception
    {

    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);

    	action.setBeneficio(beneficio);

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(false));
    	assertEquals(action.delete(), "input");

    	HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio.setId(1L);

    	action.setHistoricoBeneficio(historicoBeneficio);

    	beneficioManager.expects(once()).method("verifyExists").with(ANYTHING,ANYTHING).will(returnValue(true));
    	manager.expects(once()).method("remove").with(ANYTHING);

    	assertEquals(action.delete(), "success");
    }

    @SuppressWarnings({ "unused", "unchecked" })
    public void testGets() throws Exception
    {
		HistoricoBeneficio historicoBeneficio = action.getHistoricoBeneficio();
    	Collection<HistoricoBeneficio> historicoBeneficios = action.getHistoricoBeneficios();
    	Beneficio beneficio = action.getBeneficio();
    }
}
