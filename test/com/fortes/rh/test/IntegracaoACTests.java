package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.business.ws.RHServiceManagerTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientCargoTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientColaboradorOcorrenciaTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientColaboradorTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientGastoTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientHistoricoColaboradorTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientLotacaoTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientOcorrenciaTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientSistemaTest;

public class IntegracaoACTests extends TestSuite
{
	public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();

        // Testes do serviço WS do RH
        suite.addTestSuite(RHServiceManagerTest.class);

        // Testes dos Clientes do WS do Fortes Pessoal
        suite.addTestSuite(AcPessoalClientCargoTest.class);
        suite.addTestSuite(AcPessoalClientColaboradorOcorrenciaTest.class);
        suite.addTestSuite(AcPessoalClientColaboradorTest.class);
        suite.addTestSuite(AcPessoalClientLotacaoTest.class);
        suite.addTestSuite(AcPessoalClientOcorrenciaTest.class);
        suite.addTestSuite(AcPessoalClientSistemaTest.class);
        suite.addTestSuite(AcPessoalClientHistoricoColaboradorTest.class);
        suite.addTestSuite(AcPessoalClientGastoTest.class);
        
        return suite;
    }	
}