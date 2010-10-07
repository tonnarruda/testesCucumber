package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.selenium.acesso.TesteSeleniumLogin;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumLogoff;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumPerfil;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumUsuario;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumCadastraAreaFormacao;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumCadastraAreaInteresse;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumCadastraCandidato;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumCadastraEtapaSeletiva;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumCadastraMotivoSolicitacao;
import com.fortes.rh.test.selenium.captacao.TesteSeleniumConhecimentos;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumAplicarReajuste;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraAnalisePromocoesReajustes;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraFaixaSalarialHistorico;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraIndice;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraReajusteColetivo;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraSolicitacaoReajuste;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastraSolicitacaoReajusteTestAlert;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumCadastroGrupoOcupacional;
import com.fortes.rh.test.selenium.cargosalario.TesteSeleniumEditarSolicitacaoReajuste;
import com.fortes.rh.test.selenium.desenvolvimento.TesteSeleniumCadastraDNT;
import com.fortes.rh.test.selenium.desenvolvimento.TesteSeleniumCadastraPrioridadeTreinamento;
import com.fortes.rh.test.selenium.geral.TesteSeleniumCadastraColaborador;
import com.fortes.rh.test.selenium.geral.TesteSeleniumDesligaColaborador;
import com.fortes.rh.test.selenium.geral.TesteSeleniumImpressaoProjecaoSalarial;
import com.fortes.rh.test.selenium.pesquisa.TesteSeleniumClonarPesquisa;
import com.fortes.rh.test.selenium.pesquisa.TesteSeleniumPesquisa;

public class AllAceptTests extends AbstractSeleniumSuite
{
	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();

		//ACESSO
		suite.addTestSuite(TesteSeleniumLogin.class);
		suite.addTestSuite(TesteSeleniumPerfil.class);
		suite.addTestSuite(TesteSeleniumUsuario.class);

		//CAPTAÇÃO
		suite.addTestSuite(TesteSeleniumCadastraAreaFormacao.class);
		suite.addTestSuite(TesteSeleniumCadastraAreaInteresse.class);
		suite.addTestSuite(TesteSeleniumCadastraCandidato.class);
		suite.addTestSuite(TesteSeleniumCadastraEtapaSeletiva.class);
		suite.addTestSuite(TesteSeleniumCadastraMotivoSolicitacao.class);
		suite.addTestSuite(TesteSeleniumPerfil.class);
		suite.addTestSuite(TesteSeleniumConhecimentos.class);

		//CARGO SALARIO
		suite.addTestSuite(TesteSeleniumCadastraAnalisePromocoesReajustes.class);
		suite.addTestSuite(TesteSeleniumCadastraReajusteColetivo.class);
		suite.addTestSuite(TesteSeleniumCadastroGrupoOcupacional.class);
		suite.addTestSuite(TesteSeleniumCadastraIndice.class);
		suite.addTestSuite(TesteSeleniumCadastraFaixaSalarialHistorico.class);
		suite.addTestSuite(TesteSeleniumEditarSolicitacaoReajuste.class);
		suite.addTestSuite(TesteSeleniumCadastraSolicitacaoReajuste.class);
		suite.addTestSuite(TesteSeleniumCadastraSolicitacaoReajusteTestAlert.class);
		suite.addTestSuite(TesteSeleniumAplicarReajuste.class);

		//DESENVOLVIMENTO
		suite.addTestSuite(TesteSeleniumCadastraDNT.class);
		suite.addTestSuite(TesteSeleniumCadastraPrioridadeTreinamento.class);

		//GERAL
		suite.addTestSuite(TesteSeleniumCadastraColaborador.class);
		suite.addTestSuite(TesteSeleniumDesligaColaborador.class);
		suite.addTestSuite(TesteSeleniumImpressaoProjecaoSalarial.class);

		//PESQUISA
		suite.addTestSuite(TesteSeleniumClonarPesquisa.class);
		suite.addTestSuite(TesteSeleniumPesquisa.class);
		//suite.addTestSuite(TesteSeleniumRespondePesquisaPorAlguem.class);
		//suite.addTestSuite(TesteSeleniumResponderPesquisa.class);
		//suite.addTestSuite(TesteSeleniumResultadoPesquisa.class);
		//suite.addTestSuite(TesteSeleniumVisualizarRespostasColaborador.class);

		suite.addTestSuite(TesteSeleniumLogoff.class);

		return suite;
	}
}