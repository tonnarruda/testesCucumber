package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.selenium.integracaoAC.TesteSeleniumAreaLotacao;
import com.fortes.rh.test.selenium.integracaoAC.TesteSeleniumColaboradorOcorrenciaOcorrencia;
import com.fortes.rh.test.selenium.integracaoAC.TesteSeleniumIndice;
import com.fortes.rh.test.selenium.integracaoAC.TesteSeleniumOcorrenciaTipoOcorrencia;

public class SeleniumIntegracaoRHACTests extends AbstractSeleniumSuite
{

	
	/*
	 	Passos:
			Rodar a tarefa prepare-test-integracao-ac do ant
			Atualizar versão do dumpMinimoTestIntegracao.xml
			Rodar o GeraBancoTeste.java
			Levantar o RH
			Levantar WS AC Pessoal
	
		Atenção o script (deleteMinimoTestIntegracao.xml) para deletar funciona de baixo para cima 
		ex.: primeiro vai deletar auditoria a depois empresa
	*/
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(TesteSeleniumAreaLotacao.class);
		suite.addTestSuite(TesteSeleniumOcorrenciaTipoOcorrencia.class);
		suite.addTestSuite(TesteSeleniumColaboradorOcorrenciaOcorrencia.class);
		suite.addTestSuite(TesteSeleniumIndice.class);

		return suite;
	}
}