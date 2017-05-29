package com.fortes.rh.test.web.service;

import static com.fortes.rh.test.web.service.TipoRetornoMockCall.RETORNO_STRING;

import java.util.Date;

import mockit.Mockit;

import org.apache.axis.client.Call;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.util.mockObjects.MockCall;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientCargo;

public class AcPessoalClientCargoTest extends MockObjectTestCase
{
	private Mock acPessoalClient = null;
	private AcPessoalClientCargo acPessoalClientCargo;

    protected void setUp() throws Exception
    {
    	acPessoalClientCargo = new AcPessoalClientCargo();
    	acPessoalClient = mock(AcPessoalClient.class);

    	acPessoalClientCargo.setAcPessoalClient((AcPessoalClient)acPessoalClient.proxy());

    	Mockit.redefineMethods(Call.class, new MockCall());
    }

    public void testDeleteCargo() throws Exception
    {
    	String[] codigoACs = {"001","002"};
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(true, "", ""));
    	Mockit.redefineMethods(Call.class, mock);
    	
    	acPessoalClient.expects(once()).method("setReturnType");
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));

		assertTrue(acPessoalClientCargo.deleteCargo(codigoACs, empresa));
    }
    
    public void testDeleteCargoErro() throws Exception
    {
    	String[] codigoACs = {"001","002"};
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setCodigoAC("002022");
    	
    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(false, "Msg de Erro do AC", "Exception do AC"));
    	Mockit.redefineMethods(Call.class, mock);
    	
    	acPessoalClient.expects(once()).method("setReturnType");
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));
    	
    	assertFalse(acPessoalClientCargo.deleteCargo(codigoACs, empresa));
    }

    public void testCriarCargo() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
    	Indice indice = IndiceFactory.getEntity(1L);
    	indice.setCodigoAC("01");
    	FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity(1L);
    	faixaSalarialHistorico.setData(new Date());
    	faixaSalarialHistorico.setIndice(indice);
    	faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.VALOR);

    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));
    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(true, "", "", "1"));
    	Mockit.redefineMethods(Call.class, mock);
    	
    	acPessoalClient.expects(once()).method("setReturnType");
    	assertEquals(RETORNO_STRING, acPessoalClientCargo.criarCargo(faixaSalarial, faixaSalarialHistorico, empresa));

    }

    public void testUpdateCargo() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Cargo cargo = CargoFactory.getEntity(1L);
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity(1L);
		faixaSalarial.setCargo(cargo);
		
    	acPessoalClient.expects(once()).method("createCall").will(returnValue(new Call("http://teste")));
    	
    	MockCall mock = new MockCall();
    	mock.setProperty("TFeedbackPessoalWebService", new TFeedbackPessoalWebService(true, "", "", "1"));
    	Mockit.redefineMethods(Call.class, mock);
    	
    	acPessoalClient.expects(once()).method("setReturnType");
    	assertEquals(RETORNO_STRING, acPessoalClientCargo.createOrUpdateCargo(faixaSalarial, empresa));
    }
}
