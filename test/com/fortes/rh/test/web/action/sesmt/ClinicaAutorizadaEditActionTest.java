package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ClinicaAutorizadaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.model.sesmt.ClinicaAutorizada;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.ClinicaAutorizadaEditAction;

public class ClinicaAutorizadaEditActionTest extends MockObjectTestCase
{
	private ClinicaAutorizadaEditAction action;
	private Mock manager;
	private Mock exameManager;	

	protected void setUp() throws Exception
	{
		super.setUp();
		manager = new Mock(ClinicaAutorizadaManager.class);

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		action = new ClinicaAutorizadaEditAction();
		action.setClinicaAutorizadaManager((ClinicaAutorizadaManager) manager.proxy());
		
		exameManager = new Mock(ExameManager.class);
		action.setExameManager((ExameManager) exameManager.proxy());
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
		ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
		clinicaAutorizada.setId(1L);

		action.setClinicaAutorizada(clinicaAutorizada);

		manager.expects(once()).method("findById").with(eq(clinicaAutorizada.getId())).will(returnValue(clinicaAutorizada));
		manager.expects(atLeastOnce()).method("findTipoOutrosDistinctByEmpresa").withAnyArguments();
		Collection<Exame> exames = new ArrayList<Exame>();
		exameManager.expects(once()).method("findByEmpresaComAsoPadrao").with(ANYTHING).will(returnValue(exames));

		assertEquals(action.prepareInsert(), "success");
		assertEquals(action.getClinicaAutorizada(), clinicaAutorizada);
	}

    public void testPrepareUpdate() throws Exception
    {
    	ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
    	clinicaAutorizada.setId(1L);
    	clinicaAutorizada.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setClinicaAutorizada(clinicaAutorizada);

    	manager.expects(atLeastOnce()).method("findById").with(eq(clinicaAutorizada.getId())).will(returnValue(clinicaAutorizada));
    	manager.expects(atLeastOnce()).method("findTipoOutrosDistinctByEmpresa").withAnyArguments();
    	Collection<Exame> exames = new ArrayList<Exame>();
		exameManager.expects(atLeastOnce()).method("findByEmpresaComAsoPadrao").with(ANYTHING).will(returnValue(exames));
    	assertEquals(action.prepareUpdate(), "success");

    	manager.expects(atLeastOnce()).method("findById").with(eq(clinicaAutorizada.getId())).will(returnValue(null));
    	assertEquals(action.prepareUpdate(), "error");
    	assertNotNull(action.getActionErrors());

    }

    public void testInsert() throws Exception
    {
    	ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
    	clinicaAutorizada.setId(1L);
    	action.setClinicaAutorizada(clinicaAutorizada);

    	Collection<Exame> exames = new ArrayList<Exame>();
    	exameManager.expects(once()).method("populaExames").with(ANYTHING).will(returnValue(exames));
		manager.expects(once()).method("save").with(eq(clinicaAutorizada)).will(returnValue(clinicaAutorizada));

		assertEquals(action.insert(), "success");
    	assertEquals(action.getClinicaAutorizada(), clinicaAutorizada);

    }

    public void testUpdate() throws Exception
    {
    	ClinicaAutorizada clinicaAutorizada = new ClinicaAutorizada();
    	clinicaAutorizada.setId(1L);
    	clinicaAutorizada.setEmpresa(MockSecurityUtil.getEmpresaSession(null));
    	action.setClinicaAutorizada(clinicaAutorizada);

    	Collection<Exame> exames = new ArrayList<Exame>();
    	exameManager.expects(once()).method("populaExames").with(ANYTHING).will(returnValue(exames));
    	manager.expects(once()).method("update").with(eq(clinicaAutorizada));

    	assertEquals(action.update(), "success");
    	assertEquals(action.getClinicaAutorizada(), clinicaAutorizada);

    }

    public void testGetSet() throws Exception
    {
    	action.setClinicaAutorizada(null);
        action.getClinicaAutorizada();
        action.getModel();
        action.getTipos();
        action.setTipos(null);
        action.getExames();
        action.getExamesCheckList();
        action.setExamesCheck(new String[]{"1"});
        
    }
}