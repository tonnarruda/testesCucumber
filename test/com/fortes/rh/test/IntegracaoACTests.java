package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.web.acpessoal.AcPessoalClientCargoTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientColaboradorOcorrenciaTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientColaboradorTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientLotacaoTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientOcorrenciaTest;
import com.fortes.rh.test.web.acpessoal.AcPessoalClientSistemaTest;

public class IntegracaoACTests extends TestSuite
{
	public static TestSuite suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(AcPessoalClientLotacaoTest.class);
        suite.addTestSuite(AcPessoalClientOcorrenciaTest.class);
        suite.addTestSuite(AcPessoalClientColaboradorOcorrenciaTest.class);
        suite.addTestSuite(AcPessoalClientSistemaTest.class);
        suite.addTestSuite(AcPessoalClientCargoTest.class);
        suite.addTestSuite(AcPessoalClientColaboradorTest.class);
        
        return suite;
    }	
}