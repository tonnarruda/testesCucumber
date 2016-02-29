package com.fortes.rh.test.web.acpessoal;

import org.jmock.Mock;

import com.fortes.rh.business.geral.GrupoACManager;
import com.fortes.rh.web.ws.AcPessoalClientSistemaImpl;

public class AcPessoalClientSistemaTest extends AcPessoalClientTest
{

	private AcPessoalClientSistemaImpl acPessoalClientsistemaImpl;
	private Mock grupoACManager;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientsistemaImpl = new AcPessoalClientSistemaImpl();
		acPessoalClientsistemaImpl.setAcPessoalClient(acPessoalClientImpl);
		
		grupoACManager = mock(GrupoACManager.class);
		acPessoalClientsistemaImpl.setGrupoACManager((GrupoACManager) grupoACManager.proxy());
	}

	public void testGetVersaoWebServiceAC() throws Exception
	{
		montaMockGrupoAC();
		
		assertEquals("1.1.60.1", acPessoalClientsistemaImpl.getVersaoWebServiceAC(empresa));
	}

	public void testIdACIntegrado() throws Exception
	{
		montaMockGrupoAC();
		
		assertTrue("O SISTEMA PESSOAL ESTA DESINTEGRADO", acPessoalClientsistemaImpl.idACIntegrado(empresa));
	}

	public void testVerificaWebService() throws Exception
	{
		try
		{
			grupoACManager.expects(atLeastOnce()).method("findByCodigo").withAnyArguments().will(returnValue(grupoAC));
			acPessoalClientsistemaImpl.verificaWebService(empresa);
		} catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
}
