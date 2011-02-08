package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.web.ws.AcPessoalClientOcorrenciaImpl;

public class AcPessoalClientOcorrenciaTest extends AcPessoalClientTest
{

	private AcPessoalClientOcorrenciaImpl acPessoalClientOcorrenciaImpl;

	private TOcorrencia tocorrencia;
	private String empCodigo;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientOcorrenciaImpl = new AcPessoalClientOcorrenciaImpl();
		acPessoalClientOcorrenciaImpl.setAcPessoalClient(acPessoalClientImpl);

		tocorrencia = new TOcorrencia();
		tocorrencia.setCodigo(null);
		tocorrencia.setEmpresa(empresa.getCodigoAC());
		tocorrencia.setDescricao("Falta");		
		
		empCodigo =  getEmpresa().getCodigoAC();
	}

	@Override
	protected void tearDown() throws Exception
	{
		execute("delete from ocr where codigo > '001' and emp_codigo='" + empCodigo + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = query("select count(*) as total from ocr where emp_codigo = '" + empCodigo + "'");
		if (result.next())
			assertEquals(1, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");		
	}
	
	public void testInsertOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		assertEquals("002", codigoAC);
	}

	public void testEditarOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		assertEquals("002", codigoAC);

		tocorrencia.setCodigo(codigoAC);
		tocorrencia.setDescricao("Falta de vergonha");
		acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		
		ResultSet result = query("select descricao from ocr where codigo = '002'");
		if (result.next())
			assertEquals("Falta de vergonha", result.getString("descricao"));
		else
			fail("Consulta não retornou nada...");		
	}

	public void testDeletarOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		tocorrencia.setCodigo(codigoAC);

		ResultSet result = query("select codigo from ocr where codigo = '"+ codigoAC +"'");
		if (!result.next())
			fail("Consulta não retornou nada...");		
		
		acPessoalClientOcorrenciaImpl.removerTipoOcorrencia(tocorrencia, empresa);
		
		result = query("select codigo from ocr where codigo = '"+ codigoAC +"'");
		if (result.next())
			fail("Consulta retornou algo...");		
	}
}
