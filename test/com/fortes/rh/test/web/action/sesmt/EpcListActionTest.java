package com.fortes.rh.test.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.sesmt.EpcListAction;

public class EpcListActionTest extends MockObjectTestCase
{

	EpcListAction action;
	Mock epcManager;

	protected void setUp() throws Exception
	{
		action = new EpcListAction();

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
		epcManager = new Mock(EpcManager.class);
		action.setEpcManager((EpcManager) epcManager.proxy());
	}

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		action = null;
		epcManager = null;
        MockSecurityUtil.verifyRole = false;

		super.tearDown();
	}

	public void testExecute() throws Exception
	{
		assertEquals("success", action.execute());
	}

	public void testList() throws Exception
	{
		Collection<Epc> epcs = new ArrayList<Epc>();

		epcManager.expects(once()).method("getCount").with(ANYTHING,ANYTHING).will(returnValue(epcs.size()));
		epcManager.expects(once()).method("findToList").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING}).will(returnValue(epcs));

		assertEquals("success", action.list());
	}

	public void testDelete() throws Exception
	{
//		Epc epc = new Epc();
//		epc.setId(1L);
//		action.setEpc(epc);
//
//		Epc epcTmp = new Epc();
//		epcTmp.setId(1L);
//		Empresa empresa2 = new Empresa();
//		empresa2.setId(1L);
//		epcTmp.setEmpresa(empresa2);
//
//		epcManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(epcTmp));
//
//		epcManager.expects(once()).method("remove").with(ANYTHING);
//		assertEquals("success", action.delete());
	}

	public void testDeleteEmpresaErrada() throws Exception
	{
//		Epc epc = new Epc();
//		epc.setId(1L);
//		action.setEpc(epc);
//
//		Epc epcTmp = new Epc();
//		epcTmp.setId(1L);
//		Empresa empresa2 = new Empresa();
//		empresa2.setId(2L);
//		epcTmp.setEmpresa(empresa2);
//
//		epcManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(epcTmp));
//
//		epcManager.expects(never()).method("remove").with(ANYTHING);
//		assertEquals("error", action.delete());
	}

	public void testDeleteSemId() throws Exception
	{
		Epc epc = new Epc();
		action.setEpc(epc);

		epcManager.expects(never()).method("findById").with(ANYTHING).will(returnValue(null));

		epcManager.expects(never()).method("remove").with(ANYTHING);
		assertEquals("success", action.delete());
	}

	public void testGetSet(){
		action.getEpc();
		action.getEpcs();
	}
}
