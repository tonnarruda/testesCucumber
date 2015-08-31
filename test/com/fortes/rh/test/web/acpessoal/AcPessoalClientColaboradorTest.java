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
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
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
		execute("delete from ctt where empcodigoac='" + empCodigo + "'");
		execute("delete from epg where emp_codigo='" + empCodigo + "' and codigo='991199'");
		
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
			assertEquals(172, result.getInt("total"));
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
		
		tEmpregado.setNome("JOAO BATISTA foi editado");
		tEmpregado.setCodigoAC("000007");
		tEmpregado.setEmpresaCodigoAC(empCodigo);
		tEmpregado.setUfSigla("CE");
		tEmpregado.setCidadeCodigoAC("04400");
		
		acPessoalClientColaboradorImpl.atualizar(tEmpregado, empresa);
		
		ResultSet result = query("select nome from epg where codigo = '000007' and emp_codigo = '" + empCodigo + "'");
		if (result.next())
			assertEquals("JOAO BATISTA foi editado", result.getString("nome"));			
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testRemuneracaoVariavel() throws Exception
	{
		montaMockGrupoAC();
		
		execute("insert into fol(emp_codigo, seq, folha, dtcalculo, encerrada, acalcular, calculando, calculados) values('0006', 500, 1, '2011-02-01', 'S', 0, 0, 1)");
		execute("insert into fpg(emp_codigo, fol_seq, anomes, sequencial, dtinicial, dtfinal, tipo) values('0006', 500, '201101','01', '2011-01-01', '2011-01-31', 4)");
		execute("insert into efo(emp_codigo, fol_seq, epg_codigo, sep_data, HORASTRAB, STATUS ) values('0006', 500, '000014', '2000-01-01', 80, '1')");
		execute("insert into efp(emp_codigo, efo_fol_seq, efo_epg_codigo, eve_codigo, referencia, valor, parametro, atributo, demonstracao) values ('0006', 500, '000014', '011', '00', 99, '00', null, null)");
		execute("insert into erh (eve_codigo, buscaremfolha) values ('011', 1)");
		
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
}
