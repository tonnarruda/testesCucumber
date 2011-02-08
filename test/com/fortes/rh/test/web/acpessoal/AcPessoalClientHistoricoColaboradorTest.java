package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientTabelaReajuste;

public class AcPessoalClientHistoricoColaboradorTest extends AcPessoalClientTest
{
	private AcPessoalClientTabelaReajuste acPessoalClientTabelaReajuste;
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientTabelaReajuste = new AcPessoalClientTabelaReajuste();
		acPessoalClientTabelaReajuste.setAcPessoalClient(acPessoalClientImpl);
	}

	@Override
	protected void tearDown() throws Exception
	{
		execute("delete from rhsep");
		super.tearDown();
	}
	
	public void testUpdateHistorico() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC("220");
		
		Colaborador amanda = ColaboradorFactory.getEntity(1L);
		amanda.setCodigoAC("000029");
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		area.setCodigoAC("00101");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setCodigoAC("0001");
		
		HistoricoColaborador historicoPorValor1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoPorValor1.setData(DateUtil.montaDataByString("01/01/2000"));
		historicoPorValor1.setFaixaSalarial(faixaSalarial);
		historicoPorValor1.setColaborador(amanda);
		historicoPorValor1.setAreaOrganizacional(area);
		historicoPorValor1.setEstabelecimento(estabelecimento);
		historicoPorValor1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoPorValor1.setSalario(321.21);
		
		HistoricoColaborador historicoPorValor2 = HistoricoColaboradorFactory.getEntity(1L);
		historicoPorValor2.setData(DateUtil.montaDataByString("01/02/2000"));
		historicoPorValor2.setFaixaSalarial(faixaSalarial);
		historicoPorValor2.setColaborador(amanda);
		historicoPorValor2.setAreaOrganizacional(area);
		historicoPorValor2.setEstabelecimento(estabelecimento);
		historicoPorValor2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoPorValor2.setSalario(500.55);
		
		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(historicoPorValor1);
		
		acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicos, empresa, null, false);
		String sql = "select data,valor,sal_tipo from rhsep where epg_codigo='000029'";
		ResultSet result = query(sql);
		if (result.next())
		{
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
			assertEquals(321.21, result.getDouble("valor"));
			assertEquals("V", result.getString("sal_tipo"));
		}
		else
			fail("Consulta não retornou nada...");
		
		historicos.clear();
		historicos.add(historicoPorValor2);
		acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicos, empresa, null, false);
		
		result = query(sql);
		if (result.next())
		{
			assertEquals("2000-02-01 00:00:00.0", result.getString("data"));
			assertEquals(500.55, result.getDouble("valor"));
			assertEquals("V", result.getString("sal_tipo"));
		}
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testInsertHistoricoEmLote() throws Exception
	{
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCodigoAC("220");
		
		Colaborador amanda = ColaboradorFactory.getEntity(1L);
		amanda.setCodigoAC("000029");
		
		Colaborador lucia = ColaboradorFactory.getEntity(2L);
		lucia.setCodigoAC("000022");

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		area.setCodigoAC("00101");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		estabelecimento.setCodigoAC("0001");
		
		HistoricoColaborador historicoPorValor1 = HistoricoColaboradorFactory.getEntity(1L);
		historicoPorValor1.setData(DateUtil.montaDataByString("01/01/2000"));
		historicoPorValor1.setFaixaSalarial(faixaSalarial);
		historicoPorValor1.setColaborador(amanda);
		historicoPorValor1.setAreaOrganizacional(area);
		historicoPorValor1.setEstabelecimento(estabelecimento);
		historicoPorValor1.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoPorValor1.setSalario(321.21);
		
		HistoricoColaborador historicoPorValor2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoPorValor2.setData(DateUtil.montaDataByString("01/01/2000"));
		historicoPorValor2.setFaixaSalarial(faixaSalarial);
		historicoPorValor2.setColaborador(lucia);
		historicoPorValor2.setAreaOrganizacional(area);
		historicoPorValor2.setEstabelecimento(estabelecimento);
		historicoPorValor2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoPorValor2.setSalario(500.55);
		
		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(historicoPorValor1);
		historicos.add(historicoPorValor2);
		
		acPessoalClientTabelaReajuste.saveHistoricoColaborador(historicos, empresa, null, false);
		
		ResultSet result = query("select data,valor,sal_tipo from rhsep where epg_codigo='000029'");
		if (result.next())
		{
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
			assertEquals(321.21, result.getDouble("valor"));
			assertEquals("V", result.getString("sal_tipo"));
		}
		else
			fail("Consulta não retornou nada...");
		
		result = query("select data,valor,sal_tipo from rhsep where epg_codigo='000022'");
		if (result.next())
		{
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
			assertEquals(500.55, result.getDouble("valor"));
			assertEquals("V", result.getString("sal_tipo"));
		}
		else
			fail("Consulta não retornou nada...");
	}
}
