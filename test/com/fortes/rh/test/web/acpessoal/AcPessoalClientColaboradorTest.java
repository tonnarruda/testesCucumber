package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.CategoriaESocial;
import com.fortes.rh.model.dicionario.StatusAdmisaoColaboradorNoFortesPessoal;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorImpl;

public class AcPessoalClientColaboradorTest extends AcPessoalClientTest
{
	private TEmpregado tEmpregado;
	private TSituacao tSituacao;

	private AcPessoalClientColaboradorImpl acPessoalClientColaboradorImpl;
	private String empCodigo;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientColaboradorImpl = new AcPessoalClientColaboradorImpl();
		acPessoalClientColaboradorImpl.setAcPessoalClient(acPessoalClientImpl);

		tEmpregado = new TEmpregado();
		tEmpregado.setId(1);
		tEmpregado.setNome("Fulano da Silva");
		tEmpregado.setNomeComercial("Fulano");
		tEmpregado.setDataAdmissao("02/10/2009");
		tEmpregado.setDataNascimento("01/05/2000");
		tEmpregado.setEmpresaCodigoAC(empresa.getCodigoAC());
		
		tSituacao = new TSituacao();
		tSituacao.setId(2);
		tSituacao.setData("02/10/2009");
		
		empCodigo =  getEmpresa().getCodigoAC();
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		execute("delete from ctt where emp_codigo='" + empCodigo + "'");
		execute("delete from epg where emp_codigo='" + empCodigo + "' and codigo='991199'");
		execute("delete from rhsep where emp_codigo='" + empCodigo + "'");
		
		execute("update epg set nome='JOAO BATISTA SOARES' where codigo = '000007' and emp_codigo = '" + empCodigo + "'");
		
		//limpa dados da folha, futuramente o JUSTINO vai colocar no banco demo (07/02/2011)
		execute("delete from fol where emp_codigo='0006' and seq=500");
		execute("delete from fpg where emp_codigo='0006' and fol_seq=500");
		execute("delete from efo where emp_codigo='0006' and fol_seq=500");
		execute("delete from efp where emp_codigo='0006' and efo_fol_seq=500");
		execute("delete from erh");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = query("select count(*) as total from car where emp_codigo = '" + empCodigo + "'");
		if (result.next())
			assertTrue(result.getInt("total") >= 171);
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testContratarColaboradorACPorValor() throws Exception
	{
		montaMockGrupoAC();
		
		tSituacao.setValor(5.5);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));

		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , salariovalor from ctt where id_externo = "+ tEmpregado.getId() +" and emp_codigo = '" + empCodigo + "'");
		if (result.next())
		{
			assertEquals("Fulano da Silva", result.getString("nome"));			
			assertEquals("Fulano", result.getString("nomecomercial"));
			assertEquals("2009-10-02 00:00:00.0", result.getString("dataadmissao"));
			assertEquals("2000-05-01 00:00:00.0", result.getString("datanascimento"));
			assertEquals(3, result.getInt("sal_tipo"));
			assertEquals(5.5, result.getDouble("salariovalor"));
		}
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testContratarColaboradorACPorIndice() throws Exception
	{
		montaMockGrupoAC();
		
		tSituacao.setValor(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.INDICE + "");
		tSituacao.setIndiceQtd(2.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setIndiceCodigoAC("1000");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , ind_codigo_salario from ctt where id_externo = "+ tEmpregado.getId() +" and emp_codigo = '" + empCodigo + "'");
		if (result.next())
		{
			assertEquals("Fulano da Silva", result.getString("nome"));			
			assertEquals("Fulano", result.getString("nomecomercial"));
			assertEquals("2009-10-02 00:00:00.0", result.getString("dataadmissao"));
			assertEquals("2000-05-01 00:00:00.0", result.getString("datanascimento"));
			assertEquals(2, result.getInt("sal_tipo"));
			assertEquals("1000", result.getString("ind_codigo_salario"));
		}
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testContratarColaboradorACPorCargo() throws Exception
	{
		montaMockGrupoAC();
		
		tSituacao.setValor(0.0);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , car_codigo from ctt where id_externo = "+ tEmpregado.getId() +" and emp_codigo = '" + empCodigo + "'");
		if (result.next())
		{
			assertEquals("Fulano da Silva", result.getString("nome"));			
			assertEquals("Fulano", result.getString("nomecomercial"));
			assertEquals("2009-10-02 00:00:00.0", result.getString("dataadmissao"));
			assertEquals("2000-05-01 00:00:00.0", result.getString("datanascimento"));
			assertEquals(1, result.getInt("sal_tipo"));
			assertEquals("220", result.getString("car_codigo"));
		}
		else
			fail("Consulta não retornou nada...");
	}

	public void testEditarColaboradorAC_CTT() throws Exception
	{
		montaMockGrupoAC();
		
		tSituacao.setValor(6.7);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa);

		tEmpregado.setNome("Fulano da Silva Sauro");
		tEmpregado.setNomeComercial("Fulano Sauro");
		tSituacao.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		tSituacao.setValor(5.5);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		
		acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa);
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , salariovalor from ctt where id_externo = "+ tEmpregado.getId() +" and emp_codigo = '" + empCodigo + "'");
		if (result.next())
		{
			assertEquals("Fulano da Silva Sauro", result.getString("nome"));			
			assertEquals("Fulano Sauro", result.getString("nomecomercial"));
			assertEquals("2009-10-02 00:00:00.0", result.getString("dataadmissao"));
			assertEquals("2000-05-01 00:00:00.0", result.getString("datanascimento"));
			assertEquals(3, result.getInt("sal_tipo"));
			assertEquals(5.5, result.getDouble("salariovalor"));
		}
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testEditarColaboradorAC_EPG() throws Exception
	{
		montaMockGrupoAC();
		
		tEmpregado.setFoto("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");
		tEmpregado.setNome("JOAO BATISTA foi editado");
		tEmpregado.setCodigoAC("000007");
		tEmpregado.setEmpresaCodigoAC(empCodigo);
		tEmpregado.setUfSigla("CE");
		tEmpregado.setCidadeCodigoAC("04400");
		
		acPessoalClientColaboradorImpl.atualizar(tEmpregado, empresa, null);
		
		ResultSet result = query("select nome,foto from epg where codigo = '000007' and emp_codigo = '" + empCodigo + "'");
		if (result.next()){
			assertEquals("JOAO BATISTA foi editado", result.getString("nome"));
			assertTrue(result.getString("foto").contains("JPG:"));
		}else
			fail("Consulta não retornou nada...");
	}
	
	public void testRemuneracaoVariavel() throws Exception
	{
		montaMockGrupoAC();
		
		execute("insert into fol(emp_codigo, seq, folha, dtcalculo, encerrada, acalcular, calculando, calculados) values('0006', 500, 1, '2011-02-01', 'S', 0, 0, 1)");
		execute("insert into fpg(emp_codigo, fol_seq, anomes, sequencial, dtinicial, dtfinal, tipo) values('0006', 500, '201101','01', '2011-01-01', '2011-01-31', 4)");
		execute("insert into efo(emp_codigo, fol_seq, epg_codigo, sep_data, HORASTRAB, STATUS ) values('0006', 500, '000014', '2000-01-01', 80, '1')");
		execute("insert into efp(emp_codigo, efo_fol_seq, efo_epg_codigo, eve_codigo, referencia, valor, parametro, atributo, demonstracao) values ('0006', 500, '000014', '111', '00', 99, '00', null, null)");
		execute("insert into erh(emp_codigo, eve_codigo, buscaremfolha) values ('0006', '111', 1)");
		
		String[] codigoACs = new String[]{"000015", "000014", "000013"};
		TRemuneracaoVariavel[] remuneracaos = acPessoalClientColaboradorImpl.getRemuneracoesVariaveis(empresa, codigoACs, "201001", "202001");
		
		if (remuneracaos.length > 0)
		{
			TRemuneracaoVariavel remuneracaoVariavel = remuneracaos[0];
			assertEquals("201101", remuneracaoVariavel.getAnoMes());
			assertEquals("000014", remuneracaoVariavel.getCodigoEmpregado());
			assertEquals(99.0, remuneracaoVariavel.getValor());
		}else
			assertTrue("Consulta no AC Retornou nulo no testRemuneracaoVariavel (Banco de dados do AC pode ter novos campos com not null)", false);
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
		
		assertTrue(acPessoalClientColaboradorImpl.solicitacaoDesligamentoAc(historicos, empresa).getSucesso());
	}
	
	public void testRemoverColaboradorAC_CTT() throws Exception
	{
		montaMockGrupoAC();
		
		tSituacao.setValor(0.0);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		assertEquals(true, acPessoalClientColaboradorImpl.remove(colaborador, empresa));

		ResultSet result = query("select nome from ctt where id_externo = "+ tEmpregado.getId() +" and emp_codigo = '" + empCodigo + "'");
		if (result.next())
			fail("Consulta RETORNOU algo...");
	}
	
	public void testRemoverColaboradorAC_EPG() throws Exception
	{
		montaMockGrupoAC();
		
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empCodigo +"','991199','TESTE do RH')");
		ResultSet result = query("select nome from epg where codigo = '991199' and emp_codigo = '" + empCodigo + "'");
		if (!result.next())
			fail("Consulta RETORNOU algo...");

		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(0L);
		assertTrue(acPessoalClientColaboradorImpl.remove(colaborador, empresa));

		result = query("select nome from epg where codigo = '991199' and emp_codigo = '" + empCodigo + "'");
		if (result.next())
			fail("Consulta RETORNOU algo...");
	}

	public void testVerificaHistoricoNaFolhaAC() throws Exception
	{
		montaMockGrupoAC();
		
		assertEquals(false, acPessoalClientColaboradorImpl.verificaHistoricoNaFolhaAC(1L, "99554", empresa));
	}
	
	public void testGetReciboDePagamentoComplementar()
	{
		montaMockGrupoAC();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("000001");
		colaborador.setEmpresa(empresa);
		
		Exception exception = null;
		
		 try {
			acPessoalClientColaboradorImpl.getReciboDePagamentoComplementar(colaborador,  DateUtil.criarDataMesAno("01/2016"));
		} catch (Exception e) {
			exception = e;
		}
		 assertNotNull(exception);
	}
	
	public void testGetReciboDePagamentoAdiantamentoDeFolha()
	{
		montaMockGrupoAC();
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		colaborador.setCodigoAC("000001");
		colaborador.setEmpresa(empresa);
		
		Exception exception = null;
		
		 try {
			acPessoalClientColaboradorImpl.getReciboPagamentoAdiantamentoDeFolha(colaborador,  DateUtil.criarDataMesAno("01/2016"));
		} catch (Exception e) {
			exception = e;
		}
		 assertNotNull(exception);
	}
	
	public void testGetFerias()
	{
		montaMockGrupoAC();
		
		Exception exception = null;
		try {
			Colaborador colaborador = ColaboradorFactory.getEntity("991199", 1L);
			colaborador.setEmpresa(empresa);

			execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empCodigo +"','"+ colaborador.getCodigoAC() +"','TESTE do RH')");
			
			acPessoalClientColaboradorImpl.getFerias(empresa, new String[]{colaborador.getCodigoAC()}, null, null);
		} catch (Exception e) {
			exception = e;
		}
		 assertNull(exception);
	}
	
	public void testGetNaturalidadesAndNacionalidades()
	{
		montaMockGrupoAC();
		
		Exception exception = null;
		try {
			Colaborador colaborador = ColaboradorFactory.getEntity("991186", 1L);
			colaborador.setEmpresa(empresa);

			execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME,MUN_UFD_SIGLA_NATURALIDADE,MUN_CODIGO_NATURALIDADE,NACIONALIDADE) VALUES ('"+ empCodigo +"','"+ colaborador.getCodigoAC() +"','TESTE do RH','CE','04400','10')");
			
			TNaturalidadeAndNacionalidade[] tNaturalidadesAndNacionalidades = acPessoalClientColaboradorImpl.getNaturalidadesAndNacionalidades(empresa, new String[]{colaborador.getCodigoAC()});
			
			assertEquals("Fortaleza - CE", ((TNaturalidadeAndNacionalidade) tNaturalidadesAndNacionalidades[0]).getNaturalidade() );
			assertEquals("Brasileira", ((TNaturalidadeAndNacionalidade) tNaturalidadesAndNacionalidades[0]).getNacionalidade() );
		} catch (Exception e) {
			exception = e;
		}
		 assertNull(exception);
	}
	
	public void testExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial() throws Exception
	{
		montaMockGrupoAC();
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empCodigo +"','991199','TESTE do RH')");
		ResultSet result = query("select nome from epg where codigo = '991199' and emp_codigo = '" + empCodigo + "'");
		if (!result.next())
			fail("Consulta RETORNOU algo...");

		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(0L);
		
		assertFalse(acPessoalClientColaboradorImpl.isExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial(empresa, colaborador.getCodigoAC()));
	}
	
	public void testExisteHistoricoCadastralDoColaboradorComPendenciaNoESocialTrue() throws Exception
	{
		montaMockGrupoAC();
		execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
		execute("INSERT INTO EPG(EMP_CODIGO,CODIGO,NOME,ADMISSAODATA,STATUS,CODIGO_EVENTO) VALUES ('"+ empCodigo +"','991199','TESTE do RH','2017-01-01',3,'S-2100')");
		execute("INSERT INTO HEPG(EMP_CODIGO,EPG_CODIGO,DATA,NOME,STATUS,CODIGO_EVENTO) VALUES ('"+ empCodigo +"','991199','2017-01-01','TESTE do RH',3,'S-2100')");

		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(0L);
		
		assertTrue(acPessoalClientColaboradorImpl.isExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial(empresa, colaborador.getCodigoAC()));
		execute("delete from es_adesao where emp_codigo = '0006'");
		execute("delete from HEPG where emp_codigo = '0006'");
		execute("delete from EPG where emp_codigo = '0006'");
	}
	
	public void testHistoricoCadastralDoColaboradorEInicioVinculo() throws Exception{
		montaMockGrupoAC();
		execute("insert into es_adesao(emp_codigo, tp_amb_esocial, data, faturamento, encerracompetencia, bdunico,ativo) values('0006', 3, '2011-02-01', 0, 0, 1, 1)");
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME,ADMISSAODATA) VALUES ('"+ empCodigo +"','991199','TESTE do RH','2017-01-01' )");
		execute("INSERT INTO HEPG (EMP_CODIGO,EPG_CODIGO,DATA,NOME,STATUS,CODIGO_EVENTO) VALUES ('"+ empCodigo +"','991199','2017-01-01','TESTE do RH',7,'S-2200')");
		
		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(0L);
		
		assertTrue(acPessoalClientColaboradorImpl.isHistoricoCadastralDoColaboradorEInicioVinculo(empresa, colaborador.getCodigoAC()));
		execute("delete from es_adesao where emp_codigo = '0006'");
		execute("delete from HEPG where emp_codigo = '0006'");
		execute("delete from EPG where emp_codigo = '0006'");
	}
	
	public void testHistoricoCadastralDoColaboradorEInicioVinculoFalse() throws Exception{
		montaMockGrupoAC();
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empCodigo +"','991199','TESTE do RH')");
		ResultSet result = query("select nome from epg where codigo = '991199' and emp_codigo = '" + empCodigo + "'");
		if (!result.next())
			fail("Consulta RETORNOU algo...");

		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(0L);
		
		assertFalse(acPessoalClientColaboradorImpl.isHistoricoCadastralDoColaboradorEInicioVinculo(empresa, colaborador.getCodigoAC()));
	}
	
	public void testStatusAdmissaoNoFortesPessoalNaTabelaTemporaria() throws Exception{
		montaMockGrupoAC();
		
		tSituacao.setValor(6.7);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa);
		
		assertEquals(StatusAdmisaoColaboradorNoFortesPessoal.NA_TABELA_TEMPORARIA.getOpcao(), acPessoalClientColaboradorImpl.statusAdmissaoNoFortesPessoal(empresa, tEmpregado.getId().longValue()));
	}
	
	public void testStatusAdmissaoNoFortesPessoalEmAdmissao() throws Exception{
		montaMockGrupoAC();
		
		execute("INSERT INTO CEPG (EMP_CODIGO, ID, ID_EXTERNO, CPF, NOME) VALUES ('"+ empCodigo +"','9999','9999','01234567891','TESTE do RH')");
				
		assertEquals(StatusAdmisaoColaboradorNoFortesPessoal.EM_ADMISSAO.getOpcao(), acPessoalClientColaboradorImpl.statusAdmissaoNoFortesPessoal(empresa,9999L));
	}	

	public void testStatusAdmissaoNoFortesPessoalEmpregado() throws Exception{
		montaMockGrupoAC();
		
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME,ID_EXTERNO) VALUES ('"+ empCodigo +"','998199','TESTE do RH', '1')");
		ResultSet result = query("select nome from epg where codigo = '991199' and emp_codigo = '" + empCodigo + "'");
		if (!result.next())
			fail("Consulta RETORNOU algo...");

		Colaborador colaborador = new Colaborador();
		colaborador.setCodigoAC("991199");
		colaborador.setId(1L);
		
		assertEquals(StatusAdmisaoColaboradorNoFortesPessoal.EMPREGADO.getOpcao(), acPessoalClientColaboradorImpl.statusAdmissaoNoFortesPessoal(empresa, colaborador.getId()));
	}
	
	public void testGetUltimaCategoriaESocial() throws Exception{
		montaMockGrupoAC();
		execute("INSERT INTO EPG (EMP_CODIGO,CODIGO,NOME) VALUES ('"+ empresa.getCodigoAC() +"','991199','TESTE do RH')");
    	execute("INSERT INTO SEP (EMP_CODIGO,EPG_CODIGO,DATA,VALOR,INDQTDE,VALETRANSPORTEALIQ,HOR_CODIGO,LOT_CODIGO,"
    			+ "EXPAGENOCIV,HORASMES,HORASSEMANA,TIPOPAGAMENTO,EST_CODIGO,CAR_CODIGO,SIN_CODIGO,SALCONTRATUAL,SALTIPO,STATUS,CATEGORIAESOCIAL) "
    			+ "VALUES ('"+ empresa.getCodigoAC() +"','991199','2017-01-01',0.0,0,0,'000001','001',0,240,40,'04','0001','001','001','S','V',7,'102')");
    	
    	execute("INSERT INTO SEP (EMP_CODIGO,EPG_CODIGO,DATA,VALOR,INDQTDE,VALETRANSPORTEALIQ,HOR_CODIGO,LOT_CODIGO,"
    			+ "EXPAGENOCIV,HORASMES,HORASSEMANA,TIPOPAGAMENTO,EST_CODIGO,CAR_CODIGO,SIN_CODIGO,SALCONTRATUAL,SALTIPO,STATUS,CATEGORIAESOCIAL) "
    			+ "VALUES ('"+ empresa.getCodigoAC() +"','991199','2017-02-01',0.0,0,0,'000001','001',0,240,40,'04','0001','001','001','S','V',7,'101')");	


    	assertEquals(CategoriaESocial.CATEGORIA_101.getCodigo(), acPessoalClientColaboradorImpl.getUltimaCategoriaESocial(empresa, "991199"));
	}
}