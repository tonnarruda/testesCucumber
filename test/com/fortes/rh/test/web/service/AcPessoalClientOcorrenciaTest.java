package com.fortes.rh.test.web.service;

import mockit.Mockit;

import org.apache.axis.client.Call;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TOcorrencia;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.util.mockObjects.MockCall;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientOcorrenciaImpl;

public class AcPessoalClientOcorrenciaTest extends MockObjectTestCase
{

	private AcPessoalClientOcorrenciaImpl acPessoalClientOcorrencia;
	private Mock acPessoalClient = null;

    protected void setUp() throws Exception
    {
    	acPessoalClientOcorrencia = new AcPessoalClientOcorrenciaImpl();

    	acPessoalClient = mock(AcPessoalClient.class);
    	acPessoalClientOcorrencia.setAcPessoalClient((AcPessoalClient)acPessoalClient.proxy());

    	Mockit.redefineMethods(Call.class, new MockCall());
    }

    public void testCriarTipoOcorrencia() throws Exception
    {
    	TOcorrencia ocorrencia = new TOcorrencia();
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	// invoke() do MockCall -> RETORNO_STRING
    	String codigoAC = "1";
       	
    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(true, "", "", "1"));
    	Mockit.redefineMethods(Call.class, mock);
    	
    	acPessoalClient.expects(once()).method("setReturnType");

    	assertEquals(codigoAC, acPessoalClientOcorrencia.criarTipoOcorrencia(ocorrencia, empresa));
    }

    public void testCriarTipoOcorrenciaException() throws Exception
    {
    	TOcorrencia ocorrencia = new TOcorrencia();
    	acPessoalClient.expects(once()).method("createCall").will(throwException(new Exception("")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	Exception exception = null;

    	try
		{
			acPessoalClientOcorrencia.criarTipoOcorrencia(ocorrencia, empresa);
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testRemoverTipoOcorrencia() throws Exception
    {
    	TOcorrencia ocorrencia = new TOcorrencia();
    	ocorrencia.setCodigo("123");
    	
    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(true, "", ""));
    	Mockit.redefineMethods(Call.class, mock);

    	acPessoalClient.expects(once()).method("setReturnType");
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));

    	Empresa empresa = EmpresaFactory.getEmpresa();

    	assertEquals(true, acPessoalClientOcorrencia.removerTipoOcorrencia(ocorrencia, empresa));
    }


}
