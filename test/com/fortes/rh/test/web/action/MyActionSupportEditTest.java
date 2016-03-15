package com.fortes.rh.test.web.action;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;
import mockit.Mockit;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;

public class MyActionSupportEditTest extends TestCase
{
	MyActionSupportEdit myActionSupportEdit;

	protected void setUp()
	{
		myActionSupportEdit = new MyActionSupportEdit();
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testGetEmpresaSistema()
	{
		assertTrue(myActionSupportEdit.getEmpresaSistema().getId().equals(1L));
	}
	
	public void testGetUsuarioLogado()
	{
		assertTrue(myActionSupportEdit.getUsuarioLogado().getId().equals(1L));
	}

	public void testSetEmpresaSistema()
	{
		Empresa empresa = MockSecurityUtil.getEmpresaSession(null);

		myActionSupportEdit.setEmpresaSistema(empresa);
		assertEquals(empresa, myActionSupportEdit.getEmpresaSistema());
	}

	public void testSetActionMsg()
	{
		Collection<String> col = new ArrayList<String>();
		col.add("str 1");
		col.add("str 2");

		myActionSupportEdit.setActionMessages(col);
		myActionSupportEdit.setActionMsg("message");
		myActionSupportEdit.setActionMsg("message");
	}

	public void testGetActionMsg()
	{
		myActionSupportEdit.getActionMsg();
	}
}
