package com.fortes.rh.test.web.dwr;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.MorroManager;
import com.fortes.rh.web.dwr.MorroDWR;

public class MorroDWRTest extends MockObjectTestCase
{
	private MorroDWR morroDWR;
	private Mock morroManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		morroDWR = new MorroDWR();

		morroManager = new Mock(MorroManager.class);

		morroDWR.setMorroManager((MorroManager) morroManager.proxy());
	}

	public void testEnviar() throws Exception
	{
		assertEquals("Enviado com sucesso", morroDWR.enviar("msg", "NullPointerException", "erro...", "localhost", "Firefox, Versao: 9"));
	}
}
