package com.fortes.rh.test.web.dwr;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.MorroManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.web.dwr.MorroDWR;

public class MorroDWRTest extends MockObjectTestCase
{
	private MorroDWR morroDWR;
	private Mock morroManager;
	private Mock parametrosDoSistemaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		morroDWR = new MorroDWR();

		morroManager = new Mock(MorroManager.class);
		morroDWR.setMorroManager((MorroManager) morroManager.proxy());
		
		parametrosDoSistemaManager = new Mock(ParametrosDoSistemaManager.class);
		morroDWR.setParametrosDoSistemaManager((ParametrosDoSistemaManager)parametrosDoSistemaManager.proxy());
		
		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);
	}
	
	protected void tearDown() throws Exception
    {
        MockSecurityUtil.verifyRole = false;
    }

	public void testEnviar() throws Exception
	{
		parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(new ParametrosDoSistema()));
		morroManager.expects(once()).method("enviar").withAnyArguments().isVoid();
		assertEquals("Enviado com sucesso", morroDWR.enviar("msg", "NullPointerException", "erro...", "localhost", "Firefox, Versao: 9"));
	}
}
