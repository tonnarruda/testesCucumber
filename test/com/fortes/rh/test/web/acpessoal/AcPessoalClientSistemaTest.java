package com.fortes.rh.test.web.acpessoal;

import com.fortes.rh.web.ws.AcPessoalClientSistemaImpl;

public class AcPessoalClientSistemaTest extends AcPessoalClientTest
{

	private AcPessoalClientSistemaImpl acPessoalClientsistemaImpl;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientsistemaImpl = new AcPessoalClientSistemaImpl();
		acPessoalClientsistemaImpl.setAcPessoalClient(acPessoalClientImpl);
	}

	public void testGetVersaoWebServiceAC() throws Exception
	{
		assertEquals("1.0.1.41", acPessoalClientsistemaImpl.getVersaoWebServiceAC(empresa));
	}

	public void testIdACIntegrado() throws Exception
	{
		assertTrue(acPessoalClientsistemaImpl.idACIntegrado(empresa));
	}

	public void testVerificaWebService() throws Exception
	{
		try
		{
			acPessoalClientsistemaImpl.verificaWebService(empresa);
		} catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
}
