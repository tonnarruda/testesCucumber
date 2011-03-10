package com.fortes.rh.test.web.service;

import static com.fortes.rh.test.web.service.TipoRetornoMockCall.RETORNO_BOOLEAN;
import static com.fortes.rh.test.web.service.TipoRetornoMockCall.RETORNO_STRING;
import mockit.Mockit;

import org.apache.axis.client.Call;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TOcorrenciaEmpregado;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockCall;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorOcorrenciaImpl;

public class AcPessoalClientColaboradorOcorrenciaTest extends MockObjectTestCase
{
	private AcPessoalClientColaboradorOcorrenciaImpl acPessoalClientColaboradorOcorrencia;
	private Mock acPessoalClient = null;


    protected void setUp() throws Exception
    {
    	acPessoalClientColaboradorOcorrencia = new AcPessoalClientColaboradorOcorrenciaImpl();

    	acPessoalClient = mock(AcPessoalClient.class);
    	acPessoalClientColaboradorOcorrencia.setAcPessoalClient((AcPessoalClient)acPessoalClient.proxy());

    	Mockit.redefineMethods(Call.class, MockCall.class);
    }

    public void testCriarColaboradorOcorrencia() throws Exception
    {
    	TOcorrenciaEmpregado ocorrencia = new TOcorrenciaEmpregado();
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	// MockCall.invoke() -> RETORNO_BOOLEAN
    	assertTrue(acPessoalClientColaboradorOcorrencia.criarColaboradorOcorrencia(ocorrencia, empresa));
    }

    public void testCriarTipoColaboradorOcorrenciaException() throws Exception
    {
    	TOcorrenciaEmpregado ocorrencia = new TOcorrenciaEmpregado();
    	acPessoalClient.expects(once()).method("createCall").will(throwException(new Exception("")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Exception exception = null;

    	try
		{
			acPessoalClientColaboradorOcorrencia.criarColaboradorOcorrencia(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testRemoverTipoColaboradorOcorrencia() throws Exception
    {
    	TOcorrenciaEmpregado ocorrencia = new TOcorrenciaEmpregado();
    	ocorrencia.setCodigo("123");

    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	// MockCall.invoke() -> RETORNO_BOOLEAN
    	boolean retorno = true;

    	assertEquals(retorno, acPessoalClientColaboradorOcorrencia.removerColaboradorOcorrencia(ocorrencia, empresa));
    }

    public void testRemoverTipoColaboradorOcorrenciaException() throws Exception
    {
    	TOcorrenciaEmpregado ocorrencia = new TOcorrenciaEmpregado();
    	ocorrencia.setCodigo("123");

    	acPessoalClient.expects(once()).method("createCall").will(throwException(new Exception()));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Exception exception = null;

    	try
		{
			acPessoalClientColaboradorOcorrencia.removerColaboradorOcorrencia(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }


}
