package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrenciaImpl;
import com.fortes.rh.web.ws.AcPessoalClientOcorrenciaImpl;

public class AcPessoalClientColaboradorOcorrenciaTest extends AcPessoalClientTest
{

	private AcPessoalClientColaboradorOcorrenciaImpl acPessoalClientColaboradorOcorrenciaImpl;
	private AcPessoalClientOcorrenciaImpl acPessoalClientOcorrenciaImpl;

	private TOcorrenciaEmpregado tOcorrenciaEmpregado;
	private TOcorrencia tOcorrencia;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientColaboradorOcorrenciaImpl = new AcPessoalClientColaboradorOcorrenciaImpl();
		acPessoalClientColaboradorOcorrenciaImpl.setAcPessoalClient(acPessoalClientImpl);
		
		acPessoalClientOcorrenciaImpl = new AcPessoalClientOcorrenciaImpl();
		acPessoalClientOcorrenciaImpl.setAcPessoalClient(acPessoalClientImpl);

		tOcorrencia = new TOcorrencia();
		tOcorrencia.setCodigo(null);
		tOcorrencia.setEmpresa(empresa.getCodigoAC());
		tOcorrencia.setDescricao("Reclamação");
		
		tOcorrenciaEmpregado = new TOcorrenciaEmpregado();
		tOcorrenciaEmpregado.setCodigoEmpregado("000029");
		tOcorrenciaEmpregado.setEmpresa(empresa.getCodigoAC());
		tOcorrenciaEmpregado.setData("01/02/2010");
	}

	@Override
	protected void tearDown() throws Exception
	{
		delete("delete from ocr where codigo > '001' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		delete("delete from oce where ocr_codigo > '001' and emp_codigo='" + getEmpresa().getCodigoAC() + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		//ocr
		ResultSet result = execute("select count(*) as total from ocr where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertEquals(1, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
		
		//oce
		result = execute("select count(*) as total from oce where emp_codigo = '" + getEmpresa().getCodigoAC() + "'");
		if (result.next())
			assertEquals(0, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testCriarColaboradorOcorrencia() throws Exception
	{
		String codigoOcorrencia = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tOcorrencia, empresa);
		tOcorrenciaEmpregado.setCodigo(codigoOcorrencia);
		
		assertEquals(true, acPessoalClientColaboradorOcorrenciaImpl.criarColaboradorOcorrencia(tOcorrenciaEmpregado, empresa));
	}
	
	public void testRemoverColaboradorOcorrencia() throws Exception
	{
		String codigoOcorrencia = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tOcorrencia, empresa);
		tOcorrenciaEmpregado.setCodigo(codigoOcorrencia);

		acPessoalClientColaboradorOcorrenciaImpl.criarColaboradorOcorrencia(tOcorrenciaEmpregado, empresa);
		
		assertEquals(true, acPessoalClientColaboradorOcorrenciaImpl.removerColaboradorOcorrencia(tOcorrenciaEmpregado, empresa));
	}
}
