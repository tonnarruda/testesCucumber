package com.fortes.rh.test.web.acpessoal;

import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.web.ws.AcPessoalClientOcorrenciaImpl;

public class AcPessoalClientOcorrenciaTest extends AcPessoalClientTest
{

	private AcPessoalClientOcorrenciaImpl acPessoalClientOcorrenciaImpl;

	private TOcorrencia tocorrencia;

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
	}

	public void testInsertOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		assertEquals(3, codigoAC.length());
	}

	public void testEditarOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		assertEquals(3, codigoAC.length());

		tocorrencia.setCodigo(codigoAC);
		tocorrencia.setDescricao("Falta de vergonha");
		String updatedCodigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		assertEquals(3, updatedCodigoAC.length());
		assertEquals(codigoAC, updatedCodigoAC);
	}

	public void testDeletarOcorrenciaAC() throws Exception
	{
		String codigoAC = acPessoalClientOcorrenciaImpl.criarTipoOcorrencia(tocorrencia, empresa);
		tocorrencia.setCodigo(codigoAC);
		assertEquals(3, codigoAC.length());

		// delete
		assertTrue(acPessoalClientOcorrenciaImpl.removerTipoOcorrencia(tocorrencia, empresa));
	}
}
