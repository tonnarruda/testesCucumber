package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.MedicoCoordenadorListAction;

public class MedicoCoordenadorListActionTest extends MockObjectTestCase
{
	private MedicoCoordenadorListAction action;
	private Mock manager;

	protected void setUp() throws Exception
    {
        super.setUp();
        Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
        action = new MedicoCoordenadorListAction();
        manager = new Mock(MedicoCoordenadorManager.class);

        action.setMedicoCoordenadorManager((MedicoCoordenadorManager) manager.proxy());
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
		MedicoCoordenador medicoCoordenador = new MedicoCoordenador();
		medicoCoordenador.setId(1L);
		action.setMedicoCoordenador(medicoCoordenador);

		MedicoCoordenador engTmp = new MedicoCoordenador();
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
    	Collection<MedicoCoordenador> collMedicoCoordenador = new ArrayList<MedicoCoordenador>();

    	manager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(collMedicoCoordenador.size()));
    	manager.expects(once()).method("find").with(new Constraint[] {ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(collMedicoCoordenador));

    	assertEquals("success", action.list());

    	assertEquals(collMedicoCoordenador, action.getMedicoCoordenadors());
    }

    public void testGetSet() throws Exception
    {
    	MedicoCoordenador medicoCoordenador = null;
    	action.setMedicoCoordenador(medicoCoordenador);
    	assertTrue(action.getMedicoCoordenador() instanceof MedicoCoordenador);

    	medicoCoordenador = new MedicoCoordenador();
    	medicoCoordenador.setId(1L);
    	action.setMedicoCoordenador(medicoCoordenador);
    	assertEquals(action.getMedicoCoordenador(),medicoCoordenador);

    }
}