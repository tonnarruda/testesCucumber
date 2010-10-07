package com.fortes.rh.test;

import junit.framework.TestSuite;

import com.fortes.rh.test.selenium.acesso.TesteSeleniumAlterarSenha;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumLogoff;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumPerfil;
import com.fortes.rh.test.selenium.acesso.TesteSeleniumUsuario;
import com.fortes.rh.test.selenium.smoke.TesteSeleniumMenu;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumAvaliacaoCandidato;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumCandidato;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumConhecimento;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumEtapaSeletiva;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumMotivoSolicitacao;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumRelatorioProcessosSeletivos;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumRelatorioSolicitacaoAberta;
import com.fortes.rh.test.selenium.smoke.captacao.TesteSeleniumSolicitacaoPessoal;
import com.fortes.rh.test.selenium.smoke.cargosalario.TesteSeleniumCargo;
import com.fortes.rh.test.selenium.smoke.cargosalario.TesteSeleniumGrupoOcupacional;
import com.fortes.rh.test.selenium.smoke.cargosalario.TesteSeleniumIndice;
import com.fortes.rh.test.selenium.smoke.cargosalario.TesteSeleniumTabelaReajusteColaborador;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteDNTSelenium;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumAvaliacaoCurso;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumCertificacao;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumControleDaFrequencia;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumCurso;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumPreenchimentoDaDNT;
import com.fortes.rh.test.selenium.smoke.desenvolvimento.TesteSeleniumPrioridadeDeTreinamento;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumAniversarianteDoMes;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumAreaFormacao;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumAreaInteresse;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumAreaOrganizacional;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumBairro;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumBeneficios;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumColaborador;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumDocumentoVersao;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumEmpresa;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumEnviarMensagem;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumEstabelecimento;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumGrupoInvestimento;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumInvestimentos;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumInvestimentosDaEmpresa;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumLog;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumMotivoDemissao;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumOcorrencia;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumParametrosDoSistema;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumRelatorioMotivoDeDesligamento;
import com.fortes.rh.test.selenium.smoke.geral.TesteSeleniumRelatorioOcorrencia;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumDuracaoPreenchimentoVagaPrepare;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumDuracaoPreenchimentoVagaPrepareMotivo;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumGastoEmpresaPrepareImprimir;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumHistoricoColaboradorPrepareRelatorioPromocoes;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumIndicadorTurnOverPrepare;
import com.fortes.rh.test.selenium.smoke.indicador.TesteSeleniumProdutividadePrepareProdutividade;
import com.fortes.rh.test.selenium.smoke.pesquisa.TesteSeleniumEntrevistaDeDesligamento;
import com.fortes.rh.test.selenium.smoke.pesquisa.TesteSeleniumPesquisa;
import com.fortes.rh.test.selenium.smoke.pesquisa.TesteSeleniumResultadoEntrevistaDeDesligamento;
import com.fortes.rh.test.selenium.smoke.security.TesteSeleniumAuditoria;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumAfastamento;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumAgenda;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumAmbiente;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumCat;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumClinicaAutorizada;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumColaboradorAfastamento;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumComissao;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEPC;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEPI;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEleicao;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEngenheiroResponsavel;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEtapaProcessoEleitoral;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumEvento;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumExame;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumExtintor;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumExtintorInspecao;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumExtintorManutencao;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumFichaMedica;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumFichaMedicaPreenchida;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumMedicaoRisco;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumMedicoCoordenador;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumMudancaFuncao;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumProntuario;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumRelatorioFichaEPI;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumRelatorioPPP;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumRelatorioPPRALTCAT;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumRisco;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumSolicitacaoEpi;
import com.fortes.rh.test.selenium.smoke.sesmt.TesteSeleniumTipoEPI;

public class AllSmokeTests extends AbstractSeleniumSuite
{

	public static TestSuite suite()
	{
		TestSuite suite = new TestSuite();

		//Entra em todas as opções do menu
		suite.addTestSuite(TesteSeleniumMenu.class);

		//Ordem do menu: Cadastro R&S
		suite.addTestSuite(TesteSeleniumCandidato.class);
		suite.addTestSuite(TesteSeleniumEtapaSeletiva.class);
		suite.addTestSuite(TesteSeleniumAreaInteresse.class);
		suite.addTestSuite(TesteSeleniumAreaFormacao.class);
		suite.addTestSuite(TesteSeleniumMotivoSolicitacao.class);

		//Ordem do menu: Movimentações R&S
		suite.addTestSuite(TesteSeleniumSolicitacaoPessoal.class);

		//Ordem do menu: Relatórios R&S
		suite.addTestSuite(TesteSeleniumRelatorioSolicitacaoAberta.class);
		suite.addTestSuite(TesteSeleniumRelatorioProcessosSeletivos.class);
		suite.addTestSuite(TesteSeleniumAvaliacaoCandidato.class);

		//Ordem do menu: Cadastro C&S
		suite.addTestSuite(TesteSeleniumAreaOrganizacional.class);
		suite.addTestSuite(TesteSeleniumGrupoOcupacional.class);
		suite.addTestSuite(TesteSeleniumConhecimento.class);
		suite.addTestSuite(TesteSeleniumCargo.class);
		suite.addTestSuite(TesteSeleniumIndice.class);

		//Ordem do menu: Movimentações C&S 
		suite.addTestSuite(TesteSeleniumTabelaReajusteColaborador.class);

		//Ordem do menu: Pesquisas
		suite.addTestSuite(TesteSeleniumPesquisa.class);

		//Ordem do menu: T&D
		suite.addTestSuite(TesteSeleniumPrioridadeDeTreinamento.class);
		suite.addTestSuite(TesteSeleniumCurso.class);
		suite.addTestSuite(TesteSeleniumAvaliacaoCurso.class);
		suite.addTestSuite(TesteSeleniumCertificacao.class);
		
		suite.addTestSuite(TesteDNTSelenium.class);
		suite.addTestSuite(TesteSeleniumPreenchimentoDaDNT.class);
		suite.addTestSuite(TesteSeleniumControleDaFrequencia.class);
		
		//Ordem do menu: Info. Funcionais
		suite.addTestSuite(TesteSeleniumGrupoInvestimento.class);
		suite.addTestSuite(TesteSeleniumInvestimentos.class);
		suite.addTestSuite(TesteSeleniumBeneficios.class);
		suite.addTestSuite(TesteSeleniumMotivoDemissao.class);
		suite.addTestSuite(TesteSeleniumEntrevistaDeDesligamento.class);
		suite.addTestSuite(TesteSeleniumOcorrencia.class);
		suite.addTestSuite(TesteSeleniumColaborador.class);
		suite.addTestSuite(TesteSeleniumInvestimentosDaEmpresa.class);
		suite.addTestSuite(TesteSeleniumRelatorioOcorrencia.class);
		suite.addTestSuite(TesteSeleniumRelatorioMotivoDeDesligamento.class);
		suite.addTestSuite(TesteSeleniumResultadoEntrevistaDeDesligamento.class);
		suite.addTestSuite(TesteSeleniumAniversarianteDoMes.class);
		
		//Ordem do menu: Indicadores
		suite.addTestSuite(TesteSeleniumDuracaoPreenchimentoVagaPrepareMotivo.class);
		suite.addTestSuite(TesteSeleniumDuracaoPreenchimentoVagaPrepare.class);
		suite.addTestSuite(TesteSeleniumProdutividadePrepareProdutividade.class);
		suite.addTestSuite(TesteSeleniumHistoricoColaboradorPrepareRelatorioPromocoes.class);
		suite.addTestSuite(TesteSeleniumIndicadorTurnOverPrepare.class);
		suite.addTestSuite(TesteSeleniumGastoEmpresaPrepareImprimir.class);

		//Ordem do menu: Cadastros SESMT
		suite.addTestSuite(TesteSeleniumTipoEPI.class);
		suite.addTestSuite(TesteSeleniumEPI.class);
		suite.addTestSuite(TesteSeleniumEPC.class);
		suite.addTestSuite(TesteSeleniumRisco.class);
		suite.addTestSuite(TesteSeleniumAmbiente.class);
		suite.addTestSuite(TesteSeleniumEngenheiroResponsavel.class);
		suite.addTestSuite(TesteSeleniumExtintor.class);
		suite.addTestSuite(TesteSeleniumEtapaProcessoEleitoral.class);
		suite.addTestSuite(TesteSeleniumEleicao.class);
		suite.addTestSuite(TesteSeleniumComissao.class);
		suite.addTestSuite(TesteSeleniumEvento.class);
		suite.addTestSuite(TesteSeleniumAgenda.class);
		suite.addTestSuite(TesteSeleniumExame.class);
		suite.addTestSuite(TesteSeleniumMedicoCoordenador.class);
		suite.addTestSuite(TesteSeleniumClinicaAutorizada.class);
		suite.addTestSuite(TesteSeleniumFichaMedica.class);
		suite.addTestSuite(TesteSeleniumAfastamento.class);

		//Ordem do menu: Movimentações SESMT
		suite.addTestSuite(TesteSeleniumMedicaoRisco.class);
		suite.addTestSuite(TesteSeleniumSolicitacaoEpi.class);
		suite.addTestSuite(TesteSeleniumExtintorInspecao.class);
		suite.addTestSuite(TesteSeleniumExtintorManutencao.class);
		//Problema com versao, falta o ASO default
//		suite.addTestSuite(TesteSeleniumSolicitacaoExame.class);
		suite.addTestSuite(TesteSeleniumMudancaFuncao.class);
		suite.addTestSuite(TesteSeleniumProntuario.class);
		suite.addTestSuite(TesteSeleniumFichaMedicaPreenchida.class);
		suite.addTestSuite(TesteSeleniumColaboradorAfastamento.class);
		suite.addTestSuite(TesteSeleniumCat.class);
		
		//Ordem do menu: Relatórios SESMT
		suite.addTestSuite(TesteSeleniumRelatorioPPRALTCAT.class);
		suite.addTestSuite(TesteSeleniumRelatorioPPP.class);
		suite.addTestSuite(TesteSeleniumRelatorioFichaEPI.class);

		//Ordem do menu: Utilitarios
		suite.addTestSuite(TesteSeleniumEmpresa.class);
		suite.addTestSuite(TesteSeleniumPerfil.class);
		suite.addTestSuite(TesteSeleniumEstabelecimento.class);
		suite.addTestSuite(TesteSeleniumUsuario.class);
		suite.addTestSuite(TesteSeleniumBairro.class);
		suite.addTestSuite(TesteSeleniumAlterarSenha.class);
		suite.addTestSuite(TesteSeleniumParametrosDoSistema.class);
		suite.addTestSuite(TesteSeleniumAuditoria.class);
		suite.addTestSuite(TesteSeleniumDocumentoVersao.class);
		suite.addTestSuite(TesteSeleniumEnviarMensagem.class);
		suite.addTestSuite(TesteSeleniumLog.class);
		
		//Ordem do menu: Sair
		suite.addTestSuite(TesteSeleniumLogoff.class);
		
		return suite;
	}
}