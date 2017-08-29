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
		
		assertEquals("1.1.65.1", acPessoalClientsistemaImpl.getVersaoWebServiceAC(empresa));
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
	
	public void testAderiuAoESocialTrue() throws Exception
	{
		montaMockGrupoAC();
		
		execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
		
		assertTrue(acPessoalClientsistemaImpl.isAderiuAoESocial(empresa));
		
		execute("delete from es_adesao where emp_codigo = '0006'");
	}
	
	public void testAderiuAoESocialFalse() throws Exception
	{
		montaMockGrupoAC();
		
		assertFalse(acPessoalClientsistemaImpl.isAderiuAoESocial(empresa));
	}
}