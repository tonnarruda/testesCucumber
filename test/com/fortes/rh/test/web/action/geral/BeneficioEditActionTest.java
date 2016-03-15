package com.fortes.rh.test.web.action.geral;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.BeneficioManager;
import com.fortes.rh.business.geral.HistoricoBeneficioManager;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.HistoricoBeneficio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.factory.geral.HistoricoBeneficioFactory;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.geral.BeneficioEditAction;

public class BeneficioEditActionTest extends MockObjectTestCase
{
	private BeneficioEditAction action;
	private Mock manager;
	private Mock historicoBeneficioManager;

	protected void setUp()
	{
		action = new BeneficioEditAction();
		manager = new Mock(BeneficioManager.class);
		historicoBeneficioManager= new Mock(HistoricoBeneficioManager.class);
		action.setBeneficioManager((BeneficioManager) manager.proxy());
		action.setHistoricoBeneficioManager((HistoricoBeneficioManager) historicoBeneficioManager.proxy());

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
    	assertEquals(action.prepareInsert(), "success");
    }

    public void testPrepareUpdate() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	action.setBeneficio(beneficio);

    	HistoricoBeneficio historicoBeneficio = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio.setId(1L);
    	historicoBeneficio.setBeneficio(beneficio);

    	HistoricoBeneficio historicoBeneficio2 = HistoricoBeneficioFactory.getEntity();
    	historicoBeneficio2.setId(2L);
    	historicoBeneficio2.setBeneficio(beneficio);

    	Collection<HistoricoBeneficio> historicoBeneficios = new ArrayList<HistoricoBeneficio>();
    	historicoBeneficios.add(historicoBeneficio);
    	historicoBeneficios.add(historicoBeneficio2);

    	manager.expects(once()).method("findById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	historicoBeneficioManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(historicoBeneficios));

    	assertEquals(action.prepareUpdate(), "error");

    	Empresa empresa = new Empresa();
    	empresa.setId(1L);
    	beneficio.setEmpresa(empresa);
    	action.setBeneficio(beneficio);

    	manager.expects(once()).method("findById").with(eq(beneficio.getId())).will(returnValue(beneficio));
    	assertEquals(action.prepareUpdate(), "success");
    }

    public void testInsert() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	action.setBeneficio(beneficio);

    	manager.expects(once()).method("saveHistoricoBeneficio").with(ANYTHING,ANYTHING);

    	assertEquals(action.insert(), "success");
    }

    public void testUpdate() throws Exception
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	beneficio.setEmpresa(null);
    	action.setBeneficio(beneficio);

    	assertEquals(action.update(), "input");

    	beneficio.setEmpresa(empresa);
    	action.setBeneficio(beneficio);

    	manager.expects(once()).method("update").with(eq(beneficio));
    	assertEquals(action.update(), "success");
    }

    public void testGetsSets() throws Exception
    {
    	Beneficio beneficio = BeneficioFactory.getEntity();
    	beneficio.setId(1L);
    	action.setBeneficio(beneficio);

    	action.getBeneficio();
    	action.setBeneficio(null);
    	action.getBeneficio();
    }
}