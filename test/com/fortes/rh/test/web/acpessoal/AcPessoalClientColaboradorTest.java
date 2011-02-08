package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
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
		tSituacao.setValor(5.5);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.VALOR + "");
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));

		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , salariovalor from ctt where id = "+ tEmpregado.getId() +" and empcodigoac = '" + empCodigo + "'");
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
		tSituacao.setValor(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.INDICE + "");
		tSituacao.setIndiceQtd(2.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setIndiceCodigoAC("1000");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , ind_codigo_salario from ctt where id = "+ tEmpregado.getId() +" and empcodigoac = '" + empCodigo + "'");
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
		tSituacao.setValor(0.0);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , car_codigo from ctt where id = "+ tEmpregado.getId() +" and empcodigoac = '" + empCodigo + "'");
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
		
		ResultSet result = query("select nome,nomecomercial,dataadmissao,datanascimento,sal_tipo , salariovalor from ctt where id = "+ tEmpregado.getId() +" and empcodigoac = '" + empCodigo + "'");
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
		execute("insert into fol(emp_codigo, seq, folha, dtcalculo, encerrada) values('0006', 500, 2, '2010-01-31', 'S')");
		execute("insert into fpg(emp_codigo, fol_seq, anomes, sequencial, dtinicial, dtfinal, tipo) values('0006', 500, '201001','01', '2010-01-01', '2010-01-31', 4)");
		execute("insert into efo(emp_codigo, fol_seq, epg_codigo, sep_data) values('0006', 500, '000014', '2000-01-01')");
		execute("insert into efp(emp_codigo, efo_fol_seq, efo_epg_codigo, eve_codigo, referencia, valor, parametro, atributo, demonstracao) values ('0006', 500, '000014', '011', '00', 99, '00', null, null)");
		execute("insert into erh (eve_codigo, buscaremfolha) values ('011', 1)");
		
		String[] codigoACs = new String[]{"000015", "000014", "000013"};
		TRemuneracaoVariavel[] remuneracaos = acPessoalClientColaboradorImpl.getRemuneracoesVariaveis(empresa, codigoACs, "200001", "202001");
		TRemuneracaoVariavel remuneracaoVariavel = remuneracaos[0];
		assertEquals("201001", remuneracaoVariavel.getAnoMes());
		assertEquals("000014", remuneracaoVariavel.getCodigoEmpregado());
		assertEquals(99.0, remuneracaoVariavel.getValor());
	}
	
	public void testRemoverColaboradorAC_CTT() throws Exception
	{
		tSituacao.setValor(0.0);
		tSituacao.setIndiceQtd(0.0);
		tSituacao.setValorAnterior(0.0);
		tSituacao.setTipoSalario(TipoAplicacaoIndice.CARGO + "");
		tSituacao.setCargoCodigoAC("220");
		
		assertTrue(acPessoalClientColaboradorImpl.contratar(tEmpregado, tSituacao, empresa));
		
		Colaborador colaborador = new Colaborador();
		colaborador.setId(1L);
		assertEquals(true, acPessoalClientColaboradorImpl.remove(colaborador, empresa));

		ResultSet result = query("select nome from ctt where id = "+ tEmpregado.getId() +" and empcodigoac = '" + empCodigo + "'");
		if (result.next())
			fail("Consulta RETORNOU algo...");
	}
	
	public void testRemoverColaboradorAC_EPG() throws Exception
	{
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
		assertEquals(false, acPessoalClientColaboradorImpl.verificaHistoricoNaFolhaAC(1L, "99554", empresa));
	}
}
