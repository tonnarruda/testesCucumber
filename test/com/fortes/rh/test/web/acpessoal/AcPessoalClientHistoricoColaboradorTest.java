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
import com.fortes.rh.model.ws.TSituacao;
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
		execute("delete from SEP where EPG_CODIGO = '991199'");
		execute("delete from EPG where CODIGO = '991199'");
		execute("delete from es_adesao where emp_codigo = '0006'");
		super.tearDown();
	}
	
	public void testDeleteHistorico() throws Exception
	{
		montaMockGrupoAC();
		
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
		
		HistoricoColaborador historicoPorValor2 = HistoricoColaboradorFactory.getEntity(2L);
		historicoPorValor2.setData(DateUtil.montaDataByString("01/01/2010"));
		historicoPorValor2.setFaixaSalarial(faixaSalarial);
		historicoPorValor2.setColaborador(amanda);
		historicoPorValor2.setAreaOrganizacional(area);
		historicoPorValor2.setEstabelecimento(estabelecimento);
		historicoPorValor2.setTipoSalario(TipoAplicacaoIndice.VALOR);
		historicoPorValor2.setSalario(900.0);

		Collection<HistoricoColaborador> historicos = new ArrayList<HistoricoColaborador>();
		historicos.add(historicoPorValor1);
		historicos.add(historicoPorValor2);
		
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
		
		TSituacao situacao = new TSituacao();
		situacao.setId(2);
		situacao.setData("01/01/2010");
		situacao.setValor(0.0);
		situacao.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		situacao.setIndiceQtd(0.0);
		situacao.setValorAnterior(0.0);
		situacao.setEmpresaCodigoAC("0006");
		situacao.setEmpregadoCodigoAC(amanda.getCodigoAC());
		
		acPessoalClientTabelaReajuste.deleteHistoricoColaboradorAC(empresa, situacao);

		result = query(sql);
		if (result.next())
		{
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
			assertEquals(321.21, result.getDouble("valor"));
			assertEquals("V", result.getString("sal_tipo"));
		}
	}

	
	public void testDeleteHistoricoEmLote() throws Exception
	{
		montaMockGrupoAC();
		
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
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
		else
			fail("Consulta não retornou nada...");
		
		result = query("select data,valor,sal_tipo from rhsep where epg_codigo='000022'");
		if (result.next())
			assertEquals("2000-01-01 00:00:00.0", result.getString("data"));
		else
			fail("Consulta não retornou nada...");
		
		TSituacao situacao1 = new TSituacao();
		situacao1.setId(1);
		situacao1.setData("01/01/2000");
		situacao1.setValor(0.0);
		situacao1.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		situacao1.setIndiceQtd(0.0);
		situacao1.setValorAnterior(0.0);
		situacao1.setEmpresaCodigoAC("0006");
		situacao1.setEmpregadoCodigoAC(amanda.getCodigoAC());

		TSituacao situacao2 = new TSituacao();
		situacao2.setId(2);
		situacao2.setData("01/02/2000");
		situacao2.setValor(0.0);
		situacao2.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		situacao2.setIndiceQtd(0.0);
		situacao2.setValorAnterior(0.0);
		situacao2.setEmpresaCodigoAC("0006");
		situacao2.setEmpregadoCodigoAC(amanda.getCodigoAC());
		
		acPessoalClientTabelaReajuste.deleteHistoricoColaboradorAC(empresa, new TSituacao[]{situacao2});

		result = query("select data,valor,sal_tipo from rhsep");
		result.next();//Possui um histórico
		if (result.next())
			fail("Consulta não RETORNOU algo...");
	}
	public void testUpdateHistorico() throws Exception
	{
		montaMockGrupoAC();
		
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
		montaMockGrupoAC();
		
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
	
	public void testExisteHistoricoContratualComPendenciaNoESocialTrue() throws Exception
	{
    	montaMockGrupoAC();
    	execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
    	
    	execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empresa.getCodigoAC() +"','991199','TESTE do RH')");
    	execute("INSERT INTO SEP (EMP_CODIGO,EPG_CODIGO,DATA,VALOR,INDQTDE,VALETRANSPORTEALIQ,HOR_CODIGO,LOT_CODIGO,"
    			+ "EXPAGENOCIV,HORASMES,HORASSEMANA,TIPOPAGAMENTO,EST_CODIGO,CAR_CODIGO,SIN_CODIGO,SALCONTRATUAL,SALTIPO,STATUS) "
    			+ "VALUES ('"+ empresa.getCodigoAC() +"','991199','2017-01-01',0.0,0,0,'000001','00101',0,240,40,'04','0001','220','001','S','V',3)");
    			
		assertTrue(acPessoalClientTabelaReajuste.existeHistoricoContratualComPendenciaNoESocial(empresa, "991199"));
    }
	
	public void testExisteHistoricoContratualComPendenciaNoESocialFalse() throws Exception
	{
    	montaMockGrupoAC();
    	execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
    	
    	execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empresa.getCodigoAC() +"','991199','TESTE do RH')");
    	execute("INSERT INTO SEP (EMP_CODIGO,EPG_CODIGO,DATA,VALOR,INDQTDE,VALETRANSPORTEALIQ,HOR_CODIGO,LOT_CODIGO,"
    			+ "EXPAGENOCIV,HORASMES,HORASSEMANA,TIPOPAGAMENTO,EST_CODIGO,CAR_CODIGO,SIN_CODIGO,SALCONTRATUAL,SALTIPO,STATUS) "
    			+ "VALUES ('"+ empresa.getCodigoAC() +"','991199','2017-01-01',0.0,0,0,'000001','001',0,240,40,'04','0001','001','001','S','V',7)");

    	assertFalse(acPessoalClientTabelaReajuste.existeHistoricoContratualComPendenciaNoESocial(empresa, "991199"));
    }
    
    public void testSituacaoContratualEhInicioVinculo() throws Exception
    {
    	montaMockGrupoAC();
    	execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
    	
    	execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empresa.getCodigoAC() +"','991199','TESTE do RH')");
    	execute("INSERT INTO SEP (EMP_CODIGO,EPG_CODIGO,DATA,VALOR,INDQTDE,VALETRANSPORTEALIQ,HOR_CODIGO,LOT_CODIGO,"
    			+ "EXPAGENOCIV,HORASMES,HORASSEMANA,TIPOPAGAMENTO,EST_CODIGO,CAR_CODIGO,SIN_CODIGO,SALCONTRATUAL,SALTIPO,STATUS,CODIGO_EVENTO) "
    			+ "VALUES ('"+ empresa.getCodigoAC() +"','991199','2017-01-01',0.0,0,0,'000001','001',0,240,40,'04','0001','001','001','S','V',7,'S-2100')");

    	assertTrue(acPessoalClientTabelaReajuste.situacaoContratualEhInicioVinculo(empresa, "991199", DateUtil.criarDataMesAno(1, 1, 2017)));
    }   
 }