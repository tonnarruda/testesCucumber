package com.fortes.rh.test.dicionario;

import junit.framework.TestCase;

import com.fortes.rh.model.dicionario.MeioComunicacao;
import com.fortes.rh.model.dicionario.Operacao;

public class OperacaoTest extends TestCase
{
	private static int qtdDeOperacoesTestadas = 0;   // Utilizado para contar quantas operações estão sendo testadas
	
	public void testQtdOperacoes()
	{
		assertEquals(24, Operacao.values().length);
	}

	public void testGetHashMapGrupos()
	{
		assertEquals(7, Operacao.getHashMapGrupos().size());
	}
	
	public void testGetDescricaoById()
	{
		int i = 0;
		assertEquals("", Operacao.getDescricaoById(1212122121));
		//manter a ordem dos ids das operações
		assertEquals("Aviso de cadastro de candidato pelo módulo externo", Operacao.getDescricaoById(++i));
		assertEquals("Aviso automático da quantidade de currículos cadastros por mês", Operacao.getDescricaoById(++i));
		assertEquals("Exibir solicitações com candidatos do módulo externo", Operacao.getDescricaoById(++i));
		assertEquals("Encerramento da solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Alteração no status da solicitação de pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Enviar lembrete avaliação desempenho", Operacao.getDescricaoById(++i));
		assertEquals("Liberar pesquisa", Operacao.getDescricaoById(++i));
		assertEquals("Lembrete automático de pesquisa não liberada", Operacao.getDescricaoById(++i));
		assertEquals("Aviso automático das avaliações do período de experiência a vencer", Operacao.getDescricaoById(++i));
		assertEquals("Aviso automático de exames previstos", Operacao.getDescricaoById(++i));
		assertEquals("Liberar turma", Operacao.getDescricaoById(++i));
		assertEquals("Contratação de Colaborador no AC Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Cancelamento de Situação no AC Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Desligar colaborador no AC Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Configuração do limite de colaboradores por cargo", Operacao.getDescricaoById(++i));
		assertEquals("Aviso automático de backup", Operacao.getDescricaoById(++i));
		assertEquals("Aviso ao responder período de experiência", Operacao.getDescricaoById(++i));
		assertEquals("Cancelamento da Contratação no AC Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Notificação de não abertura de solicitação de EPI", Operacao.getDescricaoById(++i));
		assertEquals("Notificação de não entrega da solicitação de EPI", Operacao.getDescricaoById(++i));
		assertEquals("Cancelamento de Solicitação de Desligamento no AC Pessoal", Operacao.getDescricaoById(++i));
		assertEquals("Aviso de cadastro de ocorrência", Operacao.getDescricaoById(++i));
		assertEquals("Notificação ao inserir um afastamento", Operacao.getDescricaoById(++i));
		assertEquals("Notificação de contratação de colaborador", Operacao.getDescricaoById(++i));
		
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
	}

	public void testGetGrupoById()
	{
		int i = 0;
		assertEquals("", Operacao.getGrupoById(1212122121));
		//manter a ordem dos ids das operações
		assertEquals("R&S", Operacao.getGrupoById(++i));
	}
	
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
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 14
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 15
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 16
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 17
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 18
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 19
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 20
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 21
		assertEquals(2, Operacao.getMeioComunicacaosById(++i).size()); // 22
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 23 
		assertEquals(3, Operacao.getMeioComunicacaosById(++i).size()); // 24 
		
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
		
	}

	
	public void testChave()
	{
		int i = 0;
		assertEquals(++i, Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO.getId());       // 1 
		assertEquals(++i, Operacao.QTD_CURRICULOS_CADASTRADOS.getId());              // 2 
		assertEquals(++i, Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO.getId());   // 3 
		assertEquals(++i, Operacao.ENCERRAMENTO_SOLICITACAO.getId());                // 4 
		assertEquals(++i, Operacao.ALTERAR_STATUS_SOLICITACAO.getId());              // 5 
		assertEquals(++i, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.getId());    // 6 
		assertEquals(++i, Operacao.LIBERAR_QUESTIONARIO.getId());                    // 7 
		assertEquals(++i, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.getId());      // 8 
		assertEquals(++i, Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO.getId());  // 9 
		assertEquals(++i, Operacao.EXAMES_PREVISTOS.getId());                        // 10
		assertEquals(++i, Operacao.LIBERAR_TURMA.getId());                           // 11
		assertEquals(++i, Operacao.CONTRATAR_COLABORADOR.getId());                   // 12
		assertEquals(++i, Operacao.CANCELAR_SITUACAO_AC.getId());                    // 13
		assertEquals(++i, Operacao.DESLIGAR_COLABORADOR_AC.getId());                 // 14
		assertEquals(++i, Operacao.CONFIGURACAO_LIMITE_COLABORADOR.getId());         // 15
		assertEquals(++i, Operacao.BACKUP_AUTOMATICO.getId());                       // 16
		assertEquals(++i, Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA.getId()); // 17
		assertEquals(++i, Operacao.CANCELAR_CONTRATACAO_AC.getId());                 // 18
		assertEquals(++i, Operacao.LEMBRETE_ABERTURA_SOLICITACAO_EPI.getId());       // 19
		assertEquals(++i, Operacao.LEMBRETE_ENTREGA_SOLICITACAO_EPI.getId());        // 20
		assertEquals(++i, Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC.getId());    // 21
		assertEquals(++i, Operacao.CADASTRO_OCORRENCIA.getId());					 // 22
		assertEquals(++i, Operacao.AVISO_COLABORADOR_AFASTAMENTO.getId());			 // 23
		assertEquals(++i, Operacao.AVISO_COLABORADOR_CONTRATACAO.getId());			 // 24
		
		assertEquals("Quantidade de operações testadas",Operacao.values().length, i);
	}

	public void testEncerrarSolicitacao()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENCERRAMENTO_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAlterarStatusSolicitacao()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ALTERAR_STATUS_SOLICITACAO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testEnviarLembreteAvaliacaoDesempenho()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.ENVIAR_LEMBRETE_AVALIACAO_DESEMPENHO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	
	public void testLiberarQuestionario()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.LIBERAR_QUESTIONARIO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LIBERAR_QUESTIONARIO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testLembreteAutomaticoPesquisaNaoLiberada()
	{
		++qtdDeOperacoesTestadas;
		
		assertEquals(2, Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), Operacao.LEMBRETE_QUESTIONARIO_NAO_LIBERADO.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testCadastroCandidatoModuloExterno()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRO_CANDIDATO_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
//		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAvisoQtdCurriculosCadastrados()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.QTD_CURRICULOS_CADASTRADOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAvaliacaoPeriodoExperienciaVencendo()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AVALIACAO_PERIODO_EXPERIENCIA_VENCENDO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(3,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testExamesPrevistos()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.EXAMES_PREVISTOS;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testBackupAutomatico()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.BACKUP_AUTOMATICO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testCancelarSituacaoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_SITUACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(3,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	public void testCancelarContratacaoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_CONTRATACAO_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testDesligaColaboradorAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.DESLIGAR_COLABORADOR_AC;
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testSolicitacaoCandidatoModuloExterno()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.SOLICITACAO_CANDIDATOS_MODULO_EXTERNO;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testConfiguracaoLimiteColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CONFIGURACAO_LIMITE_COLABORADOR;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testLiberarTurma()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.LIBERAR_TURMA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}

	public void testContratarColaborador()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CONTRATAR_COLABORADOR;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testResponderAvaliacaoPeriodoExperiencia()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.RESPONDER_AVALIACAO_PERIODO_EXPERIENCIA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	public void testLembreteAberturaSolicitacaoEpi()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.LEMBRETE_ABERTURA_SOLICITACAO_EPI;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}

	public void testLembreteEntregaSolicitacaoEpi()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.LEMBRETE_ENTREGA_SOLICITACAO_EPI;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testCancelarSolicitacaoDesligamentoAC()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CANCELAR_SOLICITACAO_DESLIGAMENTO_AC;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testAvisoCadastroOcorrencia()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.CADASTRO_OCORRENCIA;
		
		assertEquals(2, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.CAIXA_MENSAGEM.getListEnviarPara()).size());
	}
	
	public void testAvisoColaboradorAfastamento()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AVISO_COLABORADOR_AFASTAMENTO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testAvisoColaboradorContratacao()
	{
		++qtdDeOperacoesTestadas;
		
		Operacao operacao = Operacao.AVISO_COLABORADOR_CONTRATACAO;
		
		assertEquals(3, operacao.meioComunicação().size());
		assertEquals(MeioComunicacao.EMAIL.getDescricao(), operacao.meioComunicação().values().toArray()[2]);
		assertEquals(MeioComunicacao.CAIXA_MENSAGEM.getDescricao(), operacao.meioComunicação().values().toArray()[1]);
		assertEquals(2,(MeioComunicacao.EMAIL.getListEnviarPara()).size());
	}
	
	public void testQtdDeOperacoesTestadas() 
	{
		assertEquals("Quantidade de operações testadas",Operacao.values().length, qtdDeOperacoesTestadas);		
	}
}
