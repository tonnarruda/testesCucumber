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
		execute("delete from lot where codigo >= '003' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = query("select count(*) as total from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertTrue(result.getInt("total") >= 6);
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testInsertLotacaoAC() throws Exception
	{
		montaMockGrupoAC();
		
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
		ResultSet result = query("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoACMae + "'");
		if (result.next())
		{
			assertEquals("Mamae", result.getString("nome"));
			assertEquals("0", result.getString("lot_codigo_mae"));
			assertEquals("1", result.getString("rh_lot"));
		}
		else
			fail("Consulta não retornou nada...");
		
		//filha
		result = query("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoACFilha + "'");
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
		montaMockGrupoAC();
		
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, codigoAC.length());

		areaOrganizacional.setCodigoAC(codigoAC);
		areaOrganizacional.setNome("Area 51");
		String updatedCodigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals(3, updatedCodigoAC.length());
		assertEquals(codigoAC, updatedCodigoAC);
		
		ResultSet result = query("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'");
		if (result.next())
		{
			assertEquals("Area 51", result.getString("nome"));
			assertEquals("0", result.getString("lot_codigo_mae"));
			assertEquals("1", result.getString("rh_lot"));
		}
	}

	public void testDeletarLotacaoAC() throws Exception
	{
		montaMockGrupoAC();
		
		String codigoAC = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacional, empresa);
		assertEquals("003", codigoAC);

		areaOrganizacional.setCodigoAC(codigoAC);

		// delete
		assertTrue(acPessoalClientLotacaoImpl.deleteLotacao(areaOrganizacional, empresa));
		ResultSet result = query("select nome, lot_codigo_mae, rh_lot from lot where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'");
		assertFalse(result.next());
	}
	
	public void testGetMascara() throws Exception
	{
		montaMockGrupoAC();
		assertEquals("999.99", acPessoalClientLotacaoImpl.getMascara(empresa));
	}
	
	public void testTransfereColaboradoresAreas() throws Exception
	{
		montaMockGrupoAC();
		
		String empreagdoCodigo = "000001";
		execute("update cfe set valor = '999.99.99.99' where codigo = 'MASCARAEDLOT' and emp_codigo = '0006'");
		ResultSet result = query("select lot_codigo from sep where epg_codigo = '" + empreagdoCodigo + "' and emp_codigo = '0006'");
		
		if (result.next())
		{
			String lotacaoCodigoMae = result.getString("lot_codigo");
			
			areaOrganizacional.setCodigoAC(lotacaoCodigoMae);
			AreaOrganizacional areaOrganizacionalFilha = AreaOrganizacionalFactory.getEntity(2L);
			areaOrganizacionalFilha.setNome("filha");
			areaOrganizacionalFilha.setCodigoAC(null);
			areaOrganizacionalFilha.setAreaMae(areaOrganizacional);
			String lotacaoCodigoFilha = acPessoalClientLotacaoImpl.criarLotacao(areaOrganizacionalFilha, empresa);
			
			ResultSet situacaoEmpregado = query("select lot_codigo from sep where epg_codigo = '" + empreagdoCodigo + "' and emp_codigo = '0006'");
			if (situacaoEmpregado.next()){
				assertEquals(lotacaoCodigoFilha, situacaoEmpregado.getString("lot_codigo"));
				assertFalse(lotacaoCodigoMae.equals(situacaoEmpregado.getString("lot_codigo")));
				
				//reajustar
				execute("update cfe set valor = '999.99' where codigo = 'MASCARAEDLOT' and emp_codigo = '0006'");
				execute("update SEP set lot_codigo = '" + lotacaoCodigoMae + "'  where epg_codigo = '" + empreagdoCodigo + "' and emp_codigo = '0006'");
				execute("delete from lot where codigo = '"+ lotacaoCodigoFilha + "'");
			}else
				fail("Consulta nova situacaoEmpregado não retornou nada...");
			
		}
		else
			fail("Consulta não retornou nada...");

	}
}
