package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;
import java.util.Date;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class AcPessoalClientCargoTest extends AcPessoalClientTest
{
	private AcPessoalClientCargo acPessoalClientCargo;

	private FaixaSalarial faixaSalarial;
	private FaixaSalarialHistorico faixaSalarialHistorico;
	private Date data = DateUtil.montaDataByString("01/01/2011");
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		Date data = new Date();
		
		acPessoalClientCargo = new AcPessoalClientCargo();
		acPessoalClientCargo.setAcPessoalClient(acPessoalClientImpl);

		Cargo cargo = CargoFactory.getEntity(1L);
		cargo.setNome( "Não vai pro AC");
		
		faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
		faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
		faixaSalarialHistorico.setData(data);
	}

	@Override
	protected void tearDown() throws Exception
	{
		execute("delete from rhsca where car_codigo >= '237' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		execute("delete from car where codigo >= '237' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = query("select count(*) as total from car where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertTrue(result.getInt("total") >= 171);
		else
			fail("Consulta não retornou nada...");
		
		result = query("select count(*) as total from rhsca where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertEquals(0, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testInsertEdicaoDeleteCargoACPorValor() throws Exception
	{
		montaMockGrupoAC();
		
		faixaSalarial.setNome("Motorista de Nave");
		faixaSalarial.setNomeACPessoal("Motorista de Nave AC");
		faixaSalarial.setCodigoCbo("252105");
		
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(200.00);
		faixaSalarialHistorico.setData(data);
		
		execute("delete from SCAR where car_codigo = (select codigo from car where nome = '" + faixaSalarial.getNomeACPessoal() + "')");
		execute("delete from car where nome = '" + faixaSalarial.getNomeACPessoal() + "'");
		String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
		String sql = "select data, saltipo, valor, rh_sca_id from rhsca where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and car_codigo = '" + codigoAC + "'";
		ResultSet result = query(sql);
		if (result.next())
		{
			assertEquals("2011-01-01 00:00:00.0", result.getString("data"));
			assertEquals("V", result.getString("saltipo"));
			assertEquals("200.0", result.getString("valor"));
			assertEquals("1", result.getString("rh_sca_id"));
		}
		else
			fail("Consulta não retornou nada...");
		
		
		sql = "select nome, nome_faixa_rh from car where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'";
		result = query(sql);
		if (result.next())
		{
			assertEquals(faixaSalarial.getNomeACPessoal(), result.getString("nome"));
			assertEquals(faixaSalarial.getNome(), result.getString("nome_faixa_rh"));
		}
		else
			fail("Consulta não retornou nada...");
		
		//Teste edição cargo
		faixaSalarial.setCodigoAC(codigoAC);
		faixaSalarial.setNome("Chefe Castelo RH");
		faixaSalarial.setNomeACPessoal("Castelo do AC");
		
		execute("delete from SCAR where car_codigo = (select codigo from car where nome = '" + faixaSalarial.getNomeACPessoal() + "')");
		execute("delete from car where nome = '" + faixaSalarial.getNomeACPessoal() + "'");
		acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresa);
		
		result = query(sql);
		if (result.next())
		{
			assertEquals(faixaSalarial.getNomeACPessoal(), result.getString("nome"));
			assertEquals(faixaSalarial.getNome(), result.getString("nome_faixa_rh"));
		}
		else
			fail("Consulta não retornou nada...");

		//insert novo historico
		faixaSalarialHistorico.setData(data);
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);
		faixaSalarialHistorico.setValor(222.00);
		faixaSalarial.setCodigoAC(codigoAC);
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		
		acPessoalClientCargo.criarFaixaSalarialHistorico(faixaSalarialHistorico, empresa);
		
		sql = "select data, saltipo, valor, rh_sca_id from rhsca where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and car_codigo = '"+ codigoAC +"' and data='01-01-2011'";
		result = query(sql);
		if (result.next())
		{
			assertEquals("2011-01-01 00:00:00.0", result.getString("data"));
			assertEquals("V", result.getString("saltipo"));
			assertEquals("222.0", result.getString("valor"));
			assertEquals("1", result.getString("rh_sca_id"));
		}
		else
			fail("Consulta não retornou nada...");
		
		//delete faixaSalarial historico
		acPessoalClientCargo.deleteFaixaSalarialHistorico(faixaSalarialHistorico.getId(), empresa);
		result = query(sql);
		if (result.next())
			fail("Consulta RETORNOU coisa...");
		else
			assertTrue(true);

	}

	public void testInsertEdicaoDeleteCargoACPorIndice() throws Exception
	{
		montaMockGrupoAC();
		
		Indice indice = IndiceFactory.getEntity(1L);
		indice.setCodigoAC("1000");//salario minimo no AC
		
		faixaSalarial.setNome("Motorista de Nave");
		faixaSalarial.setNomeACPessoal("Motorista de Nave AC");
		faixaSalarial.setCodigoCbo("252105");
		
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setQuantidade(5.0);
		faixaSalarialHistorico.setData(data);

		String codigoAC = acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa);
		String sqlHist = "select data, saltipo, valor, indqtde, ind_codigo_salario, rh_sca_id from rhsca where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and car_codigo = '" + codigoAC + "'";
		ResultSet result = query(sqlHist);
		if (result.next())
		{
			assertEquals("2011-01-01 00:00:00.0", result.getString("data"));
			assertEquals("I", result.getString("saltipo"));
			assertEquals("1000", result.getString("ind_codigo_salario"));
			assertEquals("5.0", result.getString("indqtde"));
			assertEquals("0.0", result.getString("valor"));
			assertEquals("1", result.getString("rh_sca_id"));
		}
		else
			fail("Consulta não retornou nada...");

		String sql = "select nome, nome_faixa_rh from car where emp_codigo = '" + getEmpresa().getCodigoAC() + "' and codigo = '" + codigoAC + "'";
		result = query(sql);
		if (result.next())
		{
			assertEquals("Motorista de Nave AC", result.getString("nome"));
			assertEquals("Motorista de Nave", result.getString("nome_faixa_rh"));
		}
		else
			fail("Consulta não retornou nada...");
		
		//delete
		assertEquals(true, acPessoalClientCargo.deleteCargo(new String[]{codigoAC}, empresa));
		result = query(sql);
		if (result.next())
			fail("Consulta RETORNOU coisa...");
		else
			assertTrue(true);

		//verifica delete do hist
		result = query(sqlHist);
		if (result.next())
			fail("Consulta RETORNOU coisa...");
		else
			assertTrue(true);
	}
}
