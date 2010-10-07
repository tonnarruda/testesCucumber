package com.fortes.rh.test.web.acpessoal;

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
