package com.fortes.rh.test.web.acpessoal;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.web.ws.AcPessoalClientLotacaoImpl;

public class AcPessoalClientLotacaoTest extends AcPessoalClientTest
{

	private AreaOrganizacional areaOrganizacional;

	private AcPessoalClientLotacaoImpl acPessoalClientLotacaoImpl;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientLotacaoImpl = new AcPessoalClientLotacaoImpl();
		acPessoalClientLotacaoImpl.setAcPessoalClient(acPessoalClientImpl);

		areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional.setCodigoAC(null);
	}

	public void testInsertLotacaoAC() throws Exception
	{
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, codigoAC.length());

		areaOrganizacional.setCodigoAC(codigoAC);
		AreaOrganizacional areaOrganizacionalFilha = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacionalFilha.setCodigoAC(null);
		areaOrganizacionalFilha.setAreaMae(areaOrganizacional);
		String codigoACFilha = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacionalFilha, empresa);
		assertEquals(codigoAC, codigoACFilha.substring(0, 3));
	}

	public void testEditarLotacaoAC() throws Exception
	{
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, codigoAC.length());

		areaOrganizacional.setCodigoAC(codigoAC);
		areaOrganizacional.setNome("Area 51");
		String updatedCodigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, updatedCodigoAC.length());
		assertEquals(codigoAC, updatedCodigoAC);
	}

	public void testDeletarLotacaoAC() throws Exception
	{
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, codigoAC.length());

		areaOrganizacional.setCodigoAC(codigoAC);

		// delete
		assertTrue(acPessoalClientLotacaoImpl.deleteLotacao(areaOrganizacional, empresa));
	}
}
