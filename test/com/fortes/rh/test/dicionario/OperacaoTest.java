package com.fortes.rh.test.dicionario;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest
{
	private static int qtdDeOperacoesTestadas;   // Utilizado para contar quantas operações estão sendo testadas
	
	@BeforeClass
	public static void setUp() throws Exception
    {
		qtdDeOperacoesTestadas = 0;
    }
	
	@Test
	public void testQtdOperacoes()
	{
		assertEquals(44, Operacao.values().length);
	}

	@Test
	public void testGetHashMapGrupos()
	{
		assertEquals(8, Operacao.getHashMapGrupos(null).size());
	}
	
	@Test
	public void testGetDescricaoById()
	{
		int i = 0;
		assertEquals("", Operacao.getDescricaoById(1212122121));
		//manter a ordem dos ids das operações
		assertEquals("Cadastrar candidato pelo módulo externo", Operacao.getDescricaoById(++i));
		assertEquals("Quantidade mensal de currículos cadastrados (Notificação automática)", Operacao.getDescricaoById(++i));
		assertEquals("Existir currículo aguardando aprovação para participar de seleção (triagem do módulo externo)", Operacao.getDescricaoById(++i));
		assertEquals("Encerrar solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Alterar status da solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Houver avaliação de desempenho a ser respondida", Operacao.getDescricaoById(++i));
		assertEquals("Liberar pesquisa", Operacao.getDescricaoById(++i));
		assertEquals("Houver pesquisa a ser liberada (Notificação periódica)", Operacao.getDescricaoById(++i));
		assertEquals("Houver avaliações do período de experiência a vencer (Notificação periódica)", Operacao.getDescricaoById(++i));
		assertEquals("Houver exames previstos (Notificação automática)", Operacao.getDescricaoById(++i));
		assertEquals("Liberar avaliação da turma", Operacao.getDescricaoById(++i));
		assertEquals("Contratar colaborador (Integração com Fortes Pessoal)", Operacao.getDescricaoById(++i));
		assertEquals("Cancelar situação no Fortes Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Desligar colaborador no Fortes Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar limite de colaboradores por cargo", Operacao.getDescricaoById(++i));
		assertEquals("Gerar backup (Notificação automática)", Operacao.getDescricaoById(++i));
		assertEquals("Responder avaliação do período de experiência/Excluir respostas", Operacao.getDescricaoById(++i));
		assertEquals("Cancelar contratação no Fortes Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Não houver abertura de solicitação de EPI após contratação (Notificação periódica)", Operacao.getDescricaoById(++i));
		assertEquals("Não houver entrega da solicitação de EPI (Notificação periódica)", Operacao.getDescricaoById(++i));
		assertEquals("Cancelar solicitação de desligamento no Fortes Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar ocorrência", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar afastamento", Operacao.getDescricaoById(++i));
		assertEquals("Contratar colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Terminar contrato temporário do colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Atualizar dados pessoais", Operacao.getDescricaoById(++i));
		assertEquals("Houver aniversariantes no dia para envio automático de cartão de aniversário", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar situação no Fortes Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Houver carteira de habilitação a vencer (Notificação periódica)", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Cadastrar solicitação de realinhamento para colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Solicitar desligamento de colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Aprovar solicitação de desligamento de colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Reprovar solicitação de desligamento de colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Criar acesso ao sistema para colaborador", Operacao.getDescricaoById(++i));
		assertEquals("Notificar quando existir cursos a vencer", Operacao.getDescricaoById(++i));
		assertEquals("Notificar quando existir certificações a vencer", Operacao.getDescricaoById(++i));
		assertEquals("Colaborador completar ano de empresa", Operacao.getDescricaoById(++i));
		assertEquals("Existir colaborador aguardando autorização para participar de uma solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Alterar status de autorização do colaborador para participar de uma solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Iniciar o período da LNT", Operacao.getDescricaoById(++i));
		assertEquals("Encerrar o período da LNT", Operacao.getDescricaoById(++i));
		assertEquals("Finalizar LNT", Operacao.getDescricaoById(++i));
		assertEquals("Colaborador for contratado (cartão de boas-vindas)", Operacao.getDescricaoById(++i));
		
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
	}

	@Test
	public void testGetGrupoById()
	{
		int i = 0;
		assertEquals("", Operacao.getGrupoById(1212122121));
		//manter a ordem dos ids das operações
		assertEquals("R&S", Operacao.getGrupoById(++i));
	}
	
	@Test
	public void testGetMeioComunicacaoById()
	{
		int i = 0;
		assertEquals(null, Operacao.getMeioComunicacaosById(1212122121));
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 1
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 2
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 3
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 4
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 5
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 6
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 7
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 8
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 9
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 10
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 11
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 12
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 13
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 14
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 15
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 16
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 17
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 18
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 19
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 20
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 21
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 22
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 23 
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 24 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 25 
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 26 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 27 
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 28 
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 29 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 30 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 31 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 32 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 33 
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 34
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 35
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 36
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 37
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 38
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 39
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 40
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 41
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 42
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 43
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 44
		
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
	}

	@Test
	public void testChave()
	{
		int i = 0;
		assertEquals(++i, Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO.getId()); 			// 1
		assertEquals(++i, Operacao.QTD_CURRICULOS_CADASTRADOS.getId()); 					// 2
		assertEquals(++i, Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO.getId()); 	// 3
		assertEquals(++i, Operacao.ENCERRAR_SOLICITACAO.getId()); 							// 4
		assertEquals(++i, Operacao.ALTERAR_STATUS_SOLICITACAO.getId()); 					// 5
		assertEquals(++i, Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.getId()); 				// 6
		assertEquals(++i, Operacao.LIBERAR_PESQUISA.getId()); 								// 7
		assertEquals(++i, Operacao.PESQUISA_NAO_LIBERADA.getId()); 							// 8
		assertEquals(++i, Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId()); 		// 9
		assertEquals(++i, Operacao.EXAMES_PREVISTOS.getId()); 								// 10
		assertEquals(++i, Operacao.LIBERAR_AVALIACAO_TURMA.getId()); 						// 11
		assertEquals(++i, Operacao.CONTRATAR_COLABORADOR_AC.getId()); 						// 12
		assertEquals(++i, Operacao.CANCELAR_SITUACAO_AC.getId()); 							// 13
		assertEquals(++i, Operacao.DESLIGAR_COLABORADOR_AC.getId()); 						// 14
		assertEquals(++i, Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO.getId()); 			// 15
		assertEquals(++i, Operacao.GERAR_BACKUP.getId()); 									// 16
		assertEquals(++i, Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()); 		// 17
		assertEquals(++i, Operacao.CANCELAR_CONTRATACAO_AC.getId()); 						// 18
		assertEquals(++i, Operacao.NAO_ABERTURA_SOLICITACAO_EPI.getId()); 					// 19
		assertEquals(++i, Operacao.NAO_ENTREGA_SOLICITACAO_EPI.getId()); 					// 20
		assertEquals(++i, Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId()); 			// 21
		assertEquals(++i, Operacao.CADASTRAR_OCORRENCIA.getId()); 							// 22
		assertEquals(++i, Operacao.CADASTRAR_AFASTAMENTO.getId()); 							// 23
		assertEquals(++i, Operacao.CONTRATAR_COLABORADOR.getId()); 							// 24
		assertEquals(++i, Operacao.TERMINO_CONTRATO_COLABORADOR.getId()); 					// 25
		assertEquals(++i, Operacao.ATUALIZAR_INFO_PESSOAIS.getId()); 						// 26
		assertEquals(++i, Operacao.ENVIAR_CARTAO_ANIVERSARIANTES.getId());					// 27
		assertEquals(++i, Operacao.CADASTRAR_SITUACAO_AC.getId());							// 28
		assertEquals(++i, Operacao.HABILITACAO_A_VENCER.getId());							// 29
		assertEquals(++i, Operacao.CADASTRAR_SOLICITACAO.getId());							// 30
		assertEquals(++i, Operacao.CADASTRAR_SOLICITACAO_REALINHAMENTO_COLABORADOR.getId());// 31
		assertEquals(++i, Operacao.SOLICITAR_DESLIGAMENTO.getId());							// 32
		assertEquals(++i, Operacao.APROVAR_SOLICITACAO_DESLIGAMENTO.getId());				// 33
		assertEquals(++i, Operacao.REPROVAR_SOLICITACAO_DESLIGAMENTO.getId());				// 34
		assertEquals(++i, Operacao.CRIAR_ACESSO_SISTEMA.getId());							// 35
		assertEquals(++i, Operacao.CURSOS_A_VENCER.getId());			     				// 36
		assertEquals(++i, Operacao.CERTIFICACOES_A_VENCER.getId());			     			// 37
		assertEquals(++i, Operacao.COLABORADORES_COM_ANO_DE_EMPRESA.getId());			    // 38
		assertEquals(++i, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB.getId());	// 39
		assertEquals(++i, Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB.getId());// 40
		assertEquals(++i, Operacao.INICIAR_PERIODO_LNT.getId());							// 41
		assertEquals(++i, Operacao.ENCERRAR_PERIODO_LNT.getId());							// 42
		assertEquals(++i, Operacao.FINALIZAR_LNT.getId());									// 43
		assertEquals(++i, Operacao.BOAS_VINDAS_COLABORADORES.getId());						// 44
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
	}

	@Test
	public void testEncerrarSolicitacao()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.ENCERRAR_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAR_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAlterarStatusSolicitacao()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testEnviarLembreteAvaliacaoDesempenho()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.AVALIACAO_DESEMPENHO_A_RESPONDER.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testLiberarQuestionario()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.LIBERAR_PESQUISA.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_PESQUISA.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testLembreteAutomaticoPesquisaNaoLiberada()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.PESQUISA_NAO_LIBERADA.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.PESQUISA_NAO_LIBERADA.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testCadastroCandidatoModuloExterno()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_CANDIDATO_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
	}
	
	@Test
	public void testAvisoQtdCurriculosCadastrados()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.QTD_CURRICULOS_CADASTRADOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAvaliacaoPeriodoExperienciaVencendo()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(5,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testExamesPrevistos()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.EXAMES_PREVISTOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testBackupAutomatico()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.GERAR_BACKUP;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testCancelarSituacaoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_SITUACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(3,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	@Test
	public void testCancelarContratacaoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_CONTRATACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testDesligaColaboradorAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.DESLIGAR_COLABORADOR_AC;
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testSolicitacaoCandidatoModuloExterno()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CURRICULO_AGUARDANDO_APROVACAO_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testConfiguracaoLimiteColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_LIMITE_COLABORADOR_CARGO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testLiberarTurma()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.LIBERAR_AVALIACAO_TURMA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}

	@Test
	public void testContratarColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CONTRATAR_COLABORADOR_AC;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testResponderAvaliacaoPeriodoExperiencia()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}

	@Test
	public void testLembreteAberturaSolicitacaoEpi()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.NAO_ABERTURA_SOLICITACAO_EPI;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	@Test
	public void testLembreteEntregaSolicitacaoEpi()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.NAO_ENTREGA_SOLICITACAO_EPI;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testCancelarSolicitacaoDesligamentoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testAvisoCadastroOcorrencia()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_OCORRENCIA;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testAvisoColaboradorAfastamento()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_AFASTAMENTO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAvisoColaboradorContratacao()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CONTRATAR_COLABORADOR;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}

	@Test
	public void testTerminoContratoTemporarioColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.TERMINO_CONTRATO_COLABORADOR;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAtualizarInfoPessoais()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.ATUALIZAR_INFO_PESSOAIS;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testEnviarCartaoAniversariantes()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.ENVIAR_CARTAO_ANIVERSARIANTES;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testCadastrarSituacaoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_SITUACAO_AC;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testHabilitacaoAVencer()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.HABILITACAO_A_VENCER;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testInserirSolicitacaoPessoal()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_SOLICITACAO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(4,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testCadastrarSolicitacaoRealinhamentoColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRAR_SOLICITACAO_REALINHAMENTO_COLABORADOR;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(6,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testSolicitarDesligamentoColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.SOLICITAR_DESLIGAMENTO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(6,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAprovarSolicitacaoDesligamentoColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.APROVAR_SOLICITACAO_DESLIGAMENTO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testReprovarSolicitacaoDesligamentoColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.REPROVAR_SOLICITACAO_DESLIGAMENTO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testCriarAcessoSistema()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CRIAR_ACESSO_SISTEMA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testNotificarCursosAVencer(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CURSOS_A_VENCER;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(6,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		
	}
	
	@Test
	public void testNotificarCertificacoesAVencer(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CERTIFICACOES_A_VENCER;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(6,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		
	}
	
	@Test
	public void testColaboradoresComAnoDeEmpresa(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.COLABORADORES_COM_ANO_DE_EMPRESA;
		
		assertEquals(2, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(3,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@Test
	public void testAutorizacaoSolicitacaoPessoal(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_INCLUIR_COLAB;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testAlteraStatusAutorizacaoSolicitacaoPessoal(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AUTORIZACAO_SOLIC_PESSOAL_GESTOR_ALTERAR_STATUS_COLAB;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(6,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(5,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testIniciarPeriodoLnt(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.INICIAR_PERIODO_LNT;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testEncerrarPeriodoLnt(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.ENCERRAR_PERIODO_LNT;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testFinalizarLnt(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.FINALIZAR_LNT;
		
		assertEquals(3, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(5,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(4,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	@Test
	public void testBoasVindasColaborador(){
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.BOAS_VINDAS_COLABORADORES;
		
		assertEquals(2, operacao.meioComunicação().size());
		
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	@AfterClass
	public static void testQtdDeOperacoesTestadas() 
	{
		assertEquals("Quantidade de operações testadas",Operacao.values().length, qtdDeOperacoesTestadas);		
	}
}
