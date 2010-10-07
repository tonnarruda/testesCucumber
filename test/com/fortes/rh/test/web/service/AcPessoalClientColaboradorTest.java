package com.fortes.rh.test.web.service;

import mockit.Mockit;

import org.apache.axis.client.Call;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import com.fortes.rh.test.util.mockObjects.MockCall;
import com.fortes.rh.web.ws.AcPessoalClient;
import com.fortes.rh.web.ws.AcPessoalClientColaboradorImpl;

public class AcPessoalClientColaboradorTest extends MockObjectTestCase
{
	private Mock acPessoalClient = null;
	private AcPessoalClientColaboradorImpl acPessoalClientColaborador;

    protected void setUp() throws Exception
    {
    	acPessoalClientColaborador = new AcPessoalClientColaboradorImpl();
    	acPessoalClient = mock(AcPessoalClient.class);

    	acPessoalClientColaborador.setAcPessoalClient((AcPessoalClient)acPessoalClient.proxy());

    	Mockit.redefineMethods(Call.class, MockCall.class);
    }

}
