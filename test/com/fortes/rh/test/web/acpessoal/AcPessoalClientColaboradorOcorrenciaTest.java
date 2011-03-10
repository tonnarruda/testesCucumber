package com.fortes.rh.test.web.acpessoal;

import java.sql.ResultSet;

import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrenciaImpl;
import com.fortes.rh.web.ws.AcPessoalClientOcorrenciaImpl;

public class AcPessoalClientColaboradorOcorrenciaTest extends AcPessoalClientTest
{

	private AcPessoalClientColaboradorOcorrenciaImpl acPessoalClientColaboradorOcorrenciaImpl;
	private AcPessoalClientOcorrenciaImpl acPessoalClientOcorrenciaImpl;

	private TOcorrenciaEmpregado tOcorrenciaEmpregado;
	
	private String empCodigo;
	private String epgCodigo = "000029";

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		acPessoalClientColaboradorOcorrenciaImpl = new AcPessoalClientColaboradorOcorrenciaImpl();
		acPessoalClientColaboradorOcorrenciaImpl.setAcPessoalClient(acPessoalClientImpl);
		
		acPessoalClientOcorrenciaImpl = new AcPessoalClientOcorrenciaImpl();
		acPessoalClientOcorrenciaImpl.setAcPessoalClient(acPessoalClientImpl);

		tOcorrenciaEmpregado = new TOcorrenciaEmpregado();
		tOcorrenciaEmpregado.setCodigoEmpregado(epgCodigo);
		tOcorrenciaEmpregado.setEmpresa(empresa.getCodigoAC());
		tOcorrenciaEmpregado.setData("01/02/2010");
		
		empCodigo =  getEmpresa().getCodigoAC();
	}

	@Override
	protected void tearDown() throws Exception
	{
		execute("delete from oce where ocr_codigo > '001' and emp_codigo='" + empCodigo + "'");
		super.tearDown();
	}
	
	public void testStatusAC() throws Exception
	{
		ResultSet result = query("select count(*) as total from oce where emp_codigo = '" + empCodigo + "'");
		if (result.next())
			assertEquals(0, result.getInt("total"));
		else
			fail("Consulta não retornou nada...");
	}
	
	public void testCriarColaboradorOcorrencia() throws Exception
	{
		montaMockGrupoAC();
		
		tOcorrenciaEmpregado.setCodigo("001");
		assertEquals(true, acPessoalClientColaboradorOcorrenciaImpl.criarColaboradorOcorrencia(tOcorrenciaEmpregado, empresa));

		ResultSet result = query("select ocr_codigo, data from oce where emp_codigo = '" + empCodigo + "' and epg_codigo ='" + epgCodigo + "'");
		if (result.next())
		{
			assertEquals("001", result.getString("ocr_codigo"));
			assertEquals("2010-02-01 00:00:00.0", result.getString("data"));			
		}
		else
			fail("Consulta não retornou nada...");		
	}
	
	public void testRemoverColaboradorOcorrencia() throws Exception
	{
		montaMockGrupoAC();
		
		tOcorrenciaEmpregado.setCodigo("001");
		acPessoalClientColaboradorOcorrenciaImpl.criarColaboradorOcorrencia(tOcorrenciaEmpregado, empresa);
		
		String sql = "select ocr_codigo, data from oce where emp_codigo = '" + empCodigo + "' and epg_codigo ='" + epgCodigo + "'"; 
		ResultSet result = query(sql);
		if (!result.next())
			fail("Consulta não retornou nada...");		
		
		assertEquals(true, acPessoalClientColaboradorOcorrenciaImpl.removerColaboradorOcorrencia(tOcorrenciaEmpregado, empresa));
		
		result = query(sql);
		if (result.next())
			fail("Consulta retornou algo...");
	}
}
