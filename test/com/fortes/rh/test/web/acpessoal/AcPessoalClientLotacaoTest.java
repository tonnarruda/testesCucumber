package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

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

	@Override
	protected void tearDown() throws Exception
	{
		delete("delete from lot where codigo >= '003' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = execute("select count(*) as total from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertEquals(6, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testInsertLotacaoAC() throws Exception
	{
		//mae
		areaOrganizacional.setNome("Mamae");
		String codigoACMae = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals("003", codigoACMae);

		//filha
		areaOrganizacional.setCodigoAC(codigoACMae);
		AreaOrganizacional areaOrganizacionalFilha = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacionalFilha.setNome("filha");
		areaOrganizacionalFilha.setCodigoAC(null);
		areaOrganizacionalFilha.setAreaMae(areaOrganizacional);
		String codigoACFilha = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacionalFilha, empresa);
		assertEquals("00301", codigoACFilha);
		
		//mae
		ResultSet result = execute("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoACMae + "'");
		if (result.next())
		{
			assertEquals("Mamae", result.getString("nome"));
			assertEquals("0", result.getString("lot_codigo_mae"));
			assertEquals("1", result.getString("rh_lot"));
		}
		else
			fail("Consulta não retornou nada...");
		
		//filha
		result = execute("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoACFilha + "'");
		if (result.next())
		{
			assertEquals("filha", result.getString("nome"));
			assertEquals("003", result.getString("lot_codigo_mae"));
			assertEquals("1", result.getString("rh_lot"));
		}
		else
			fail("Consulta não retornou nada...");
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
		
		ResultSet result = execute("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'");
		if (result.next())
		{
			assertEquals("Area 51", result.getString("nome"));
			assertEquals("0", result.getString("lot_codigo_mae"));
			assertEquals("1", result.getString("rh_lot"));
		}
	}

	public void testDeletarLotacaoAC() throws Exception
	{
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals("003", codigoAC);

		areaOrganizacional.setCodigoAC(codigoAC);

		// delete
		assertTrue(acPessoalClientLotacaoImpl.deleteLotacao(areaOrganizacional, empresa));
		ResultSet result = execute("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'");
		assertFalse(result.next());
	}
}
